package az.shopery.blog_ms.service.impl;

import az.shopery.blog_ms.handler.exception.ApplicationException;
import az.shopery.blog_ms.handler.exception.ResourceNotFoundException;
import az.shopery.blog_ms.mapper.BlogMapper;
import az.shopery.blog_ms.model.dto.response.BlogResponseDto;
import az.shopery.blog_ms.model.dto.shared.SuccessResponse;
import az.shopery.blog_ms.model.entity.BlogEntity;
import az.shopery.blog_ms.model.entity.BlogLikeEntity;
import az.shopery.blog_ms.model.entity.UserEntity;
import az.shopery.blog_ms.repository.BlogLikeRepository;
import az.shopery.blog_ms.repository.BlogRepository;
import az.shopery.blog_ms.repository.UserRepository;
import az.shopery.blog_ms.service.BlogLikeService;
import az.shopery.blog_ms.util.enums.UserStatus;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogLikeServiceImpl implements BlogLikeService {

    private final BlogLikeRepository blogLikeRepository;
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final BlogMapper blogMapper;

    @Override
    @Transactional
    public SuccessResponse<Void> toggleBlogLike(String userEmail, String blogId) {
        UUID id = parse(blogId);
        UserEntity user = userRepository.findByEmailAndStatus(userEmail, UserStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + userEmail + " not found."));
        BlogEntity blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog with id " + id + " not found."));

        if (blogLikeRepository.existsByUserEmailAndBlog(userEmail, blog)) {
            blogLikeRepository.deleteByUserEmailAndBlog(userEmail, blog);
            return SuccessResponse.of("Blog unliked successfully!");
        }

        try {
            BlogLikeEntity blogLikeEntity = BlogLikeEntity.builder()
                    .user(user)
                    .blog(blog)
                    .build();
            blogLikeRepository.save(blogLikeEntity);
            return SuccessResponse.of("Blog liked successfully!");
        } catch (DataIntegrityViolationException e) {
            return SuccessResponse.of("Blog is already liked!");
        }
    }

    @Override
    @Transactional
    public SuccessResponse<Page<BlogResponseDto>> getLikedBlogs(String userEmail, Pageable pageable) {
       Page<BlogLikeEntity> blogLikeEntities = blogLikeRepository.findAllByUserEmailOrderByLikedAtDesc(userEmail, pageable);
       return SuccessResponse.of(blogLikeEntities.map((blogLikeEntity) -> blogMapper.toDto(blogLikeEntity.getBlog())),"Liked blogs retrieved successfully!");
    }

    private static UUID parse(String uuidString) {
        try {
            return UUID.fromString(uuidString.trim());
        } catch (IllegalArgumentException exception) {
            throw new ApplicationException("It is not a valid UUID format!");
        }
    }
}
