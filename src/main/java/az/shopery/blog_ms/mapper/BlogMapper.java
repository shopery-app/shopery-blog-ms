package az.shopery.blog_ms.mapper;

import az.shopery.blog_ms.client.AwsClient;
import az.shopery.blog_ms.model.dto.response.BlogResponseDto;
import az.shopery.blog_ms.model.dto.shared.AuthorDto;
import az.shopery.blog_ms.model.entity.BlogEntity;
import az.shopery.blog_ms.repository.BlogLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlogMapper {

    private final AwsClient awsClient;
    private final BlogLikeRepository blogLikeRepository;

    public BlogResponseDto toDto(BlogEntity blogEntity) {
        String presignedUrl = awsClient.getPresignedUrl(blogEntity.getImageUrl()).getBody();
        String profilePresignedUrl = awsClient.getPresignedUrl(blogEntity.getUser().getProfilePhotoUrl()).getBody();

        return BlogResponseDto.builder()
                .id(blogEntity.getId())
                .blogTitle(blogEntity.getBlogTitle())
                .content(blogEntity.getContent())
                .imageUrl(presignedUrl)
                .createdAt(blogEntity.getCreatedAt())
                .updatedAt(blogEntity.getUpdatedAt())
                .likeCount(blogLikeRepository.countByBlog(blogEntity))
                .author(AuthorDto.builder()
                        .name(blogEntity.getUser().getName())
                        .profilePhotoUrl(profilePresignedUrl)
                        .build())
                .build();
    }
}
