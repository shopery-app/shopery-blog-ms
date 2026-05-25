package az.shopery.blog_ms.service.impl;

import az.shopery.blog_ms.client.AwsClient;
import az.shopery.blog_ms.handler.exception.ApplicationException;
import az.shopery.blog_ms.handler.exception.ResourceNotFoundException;
import az.shopery.blog_ms.mapper.BlogMapper;
import az.shopery.blog_ms.model.dto.request.BlogRequestDto;
import az.shopery.blog_ms.model.dto.response.BlogResponseDto;
import az.shopery.blog_ms.model.dto.shared.SuccessResponse;
import az.shopery.blog_ms.model.entity.BlogEntity;
import az.shopery.blog_ms.model.entity.SavedBlogEntity;
import az.shopery.blog_ms.model.entity.UserEntity;
import az.shopery.blog_ms.repository.BlogRepository;
import az.shopery.blog_ms.repository.SavedBlogRepository;
import az.shopery.blog_ms.repository.UserRepository;
import az.shopery.blog_ms.service.BlogService;
import az.shopery.blog_ms.util.enums.UserStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.Objects;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final AwsClient awsClient;
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final BlogMapper blogMapper;
    private final SavedBlogRepository savedBlogRepository;

    @Override
    @Transactional
    public SuccessResponse<Page<BlogResponseDto>> getMyBlogs(String userEmail, Pageable pageable) {
        Page<BlogEntity> blogs = blogRepository.findAllByUserEmailAndIsArchived(userEmail, Boolean.FALSE, pageable);
        return SuccessResponse.of(blogs.map(blogMapper::toDto), "Your blogs retrieved successfully!");
    }

    @Override
    @Transactional
    public SuccessResponse<BlogResponseDto> getMyBlog(String userEmail, String blogId) {
        BlogEntity blogEntity = getUserOwnedBlog(blogId, userEmail);
        return SuccessResponse.of(blogMapper.toDto(blogEntity), "Your blog retrieved successfully!");
    }

    @Override
    @Transactional
    public SuccessResponse<Void> toggleBlogSave(String userEmail, String blogId) {
        UserEntity userEntity = getUserByEmail(userEmail);
        BlogEntity blogEntity = blogRepository.findByIdAndIsArchived(parse(blogId), Boolean.FALSE)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found"));
        Boolean isSaved = savedBlogRepository.existsByBlogAndUser(blogEntity, userEntity);

        if (isSaved) {
            savedBlogRepository.deleteByBlog(blogEntity);
            return SuccessResponse.of("Blog has been unsaved successfully");
        }

        SavedBlogEntity savedBlogEntity = SavedBlogEntity.builder()
                .blog(blogEntity)
                .user(userEntity)
                .build();

        savedBlogRepository.save(savedBlogEntity);
        return SuccessResponse.of("Blog has been saved successfully");
    }

    @Override
    @Transactional
    public SuccessResponse<Page<BlogResponseDto>> getSavedBlogs(String userEmail, Pageable pageable) {
        UserEntity userEntity = getUserByEmail(userEmail);
        Page<SavedBlogEntity> savedBlogEntities = savedBlogRepository.findAllByUserIdAndIsArchived(userEntity.getId(), Boolean.FALSE, pageable);
        return SuccessResponse.of(savedBlogEntities.map((savedBlogEntity) -> blogMapper.toDto(savedBlogEntity.getBlog())), "Saved blogs have been retrieved successfully");
    }

    @Override
    public SuccessResponse<Void> toggleBlogArchive(String userEmail, String blogId) {
        BlogEntity blogEntity = blogRepository.findBlogByIdAndUserEmail(parse(blogId), userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Blog with id: " + blogId + " not found!"));
        SavedBlogEntity savedBlogEntity = savedBlogRepository.findByBlog(blogEntity).orElse(null);
        if (blogEntity.getIsArchived()) {
            if (Objects.nonNull(savedBlogEntity)) {
                savedBlogEntity.setIsArchived(Boolean.FALSE);
                savedBlogRepository.save(savedBlogEntity);
            }
            blogEntity.setIsArchived(Boolean.FALSE);
            blogRepository.save(blogEntity);
            return SuccessResponse.of("Blog has been unarchived successfully!");
        }

        if (Objects.nonNull(savedBlogEntity)) {
            savedBlogEntity.setIsArchived(Boolean.TRUE);
            savedBlogRepository.save(savedBlogEntity);
        }
        blogEntity.setIsArchived(Boolean.TRUE);
        blogRepository.save(blogEntity);
        return SuccessResponse.of("Blog has been archived successfully!");
    }

    @Override
    @Transactional
    public SuccessResponse<Page<BlogResponseDto>> getArchivedBlogs(String userEmail, Pageable pageable) {
        Page<BlogEntity> archivedBlogs = blogRepository.findAllByUserEmailAndIsArchived(userEmail, Boolean.TRUE, pageable);
        return SuccessResponse.of(archivedBlogs.map(blogMapper::toDto), "Archived blogs retrieved successfully!");
    }

    @Override
    @Transactional
    public SuccessResponse<Page<BlogResponseDto>> getAllBlogs(Pageable pageable) {
        Page<BlogEntity> blogs = blogRepository.findAllByIsArchived(Boolean.FALSE, pageable);
        return SuccessResponse.of(blogs.map(blogMapper::toDto), "All blogs retrieved successfully!");
    }

    @Override
    @Transactional(readOnly = true)
    public SuccessResponse<Page<BlogResponseDto>> search(String query, Pageable pageable) {
        Page<BlogEntity> blogs = blogRepository.searchBlogs(query.trim(), pageable);
        return SuccessResponse.of(blogs.map(blogMapper::toDto), "Search results retrieved successfully!");
    }

    @Override
    @Transactional
    public SuccessResponse<Void> deleteMyBlog(String userEmail, String blogId) {
        BlogEntity blogEntity = getUserOwnedBlog(blogId, userEmail);

        String imageKey = blogEntity.getImageUrl();
        awsClient.deleteFile(imageKey);
        blogRepository.delete(blogEntity);
        return SuccessResponse.of("Blog deleted successfully!");
    }

    @Override
    public SuccessResponse<BlogResponseDto> addMyBlog(String userEmail, BlogRequestDto blogRequestDto) {
        UserEntity user = userRepository.findByEmailAndStatus(userEmail, UserStatus.ACTIVE)
                .orElseThrow(() -> new ApplicationException("User with email " + userEmail + " not found."));

        BlogEntity blogEntity = BlogEntity.builder()
                .user(user)
                .blogTitle(blogRequestDto.getTitle())
                .content(blogRequestDto.getContent())
                .build();

        blogRepository.save(blogEntity);

        return SuccessResponse.of(blogMapper.toDto(blogEntity), "Blog created successfully!");
    }

    @Override
    public SuccessResponse<String> updateBlogImage(String userEmail, String blogId, MultipartFile imageFile) {
        BlogEntity blogEntity = getUserOwnedBlog(blogId, userEmail);

        String oldImageUrlKey = blogEntity.getImageUrl();
        String newImageUrlKey = awsClient.updateFile(oldImageUrlKey, imageFile).getBody();

        blogEntity.setImageUrl(newImageUrlKey);
        blogRepository.save(blogEntity);

        String presignedUrl = awsClient.getPresignedUrl(newImageUrlKey).getBody();
        return SuccessResponse.of(presignedUrl, "Blog image updated successfully!");
    }

    @Override
    public SuccessResponse<String> deleteBlogImage(String userEmail, String blogId) {
        BlogEntity blogEntity = getUserOwnedBlog(blogId, userEmail);

        String imageKey = blogEntity.getImageUrl();
        if (Objects.isNull(imageKey) || imageKey.isBlank()) {
            throw new ResourceNotFoundException("No blog image found for blog: " + blogId);
        }

        awsClient.deleteFile(imageKey);

        blogEntity.setImageUrl(null);
        blogRepository.save(blogEntity);
        log.info("Blog image deleted successfully for blog {}", blogEntity.getBlogTitle());
        return SuccessResponse.of(null, "Blog image deleted successfully!");
    }

    @Override
    @Transactional
    public SuccessResponse<BlogResponseDto> updateMyBlog(String userEmail, BlogRequestDto blogRequestDto, String blogId) {
        BlogEntity blogEntity = getUserOwnedBlog(blogId, userEmail);

        blogEntity.setBlogTitle(blogRequestDto.getTitle());
        blogEntity.setContent(blogRequestDto.getContent());
        BlogEntity updatedBlogEntity = blogRepository.saveAndFlush(blogEntity);
        return SuccessResponse.of(blogMapper.toDto(updatedBlogEntity), "Blog updated successfully!");
    }

    private BlogEntity getUserOwnedBlog(String blogId, String userEmail) {
        return blogRepository.findByIdAndUserEmailAndIsArchived(parse(blogId), userEmail, Boolean.FALSE)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found with id: " + blogId));
    }

    private UserEntity getUserByEmail(String userEmail) {
        return userRepository.findByEmailAndStatus(userEmail, UserStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail));
    }

    private static UUID parse(String uuidString) {
        try {
            return UUID.fromString(uuidString.trim());
        } catch (IllegalArgumentException exception) {
            throw new ApplicationException("It is not a valid UUID format!");
        }
    }
}
