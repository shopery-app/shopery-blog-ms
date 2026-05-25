package az.shopery.blog_ms.mapper;

import az.shopery.blog_ms.model.dto.response.BlogResponseDto;
import az.shopery.blog_ms.model.dto.shared.AuthorDto;
import az.shopery.blog_ms.model.entity.BlogEntity;
import az.shopery.blog_ms.repository.BlogLikeRepository;
import az.shopery.blog_ms.util.aws.S3FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlogMapper {

    private final BlogLikeRepository blogLikeRepository;
    private final S3FileUtil s3FileUtil;

    public BlogResponseDto toDto(BlogEntity blogEntity) {
        return BlogResponseDto.builder()
                .id(blogEntity.getId())
                .blogTitle(blogEntity.getBlogTitle())
                .content(blogEntity.getContent())
                .imageUrl(s3FileUtil.generatePresignedUrl(blogEntity.getImageUrl()))
                .createdAt(blogEntity.getCreatedAt())
                .updatedAt(blogEntity.getUpdatedAt())
                .likeCount(blogLikeRepository.countByBlog(blogEntity))
                .author(AuthorDto.builder()
                        .name(blogEntity.getUser().getName())
                        .profilePhotoUrl(s3FileUtil.generatePresignedUrl(blogEntity.getUser().getProfilePhotoUrl()))
                        .build())
                .build();
    }
}
