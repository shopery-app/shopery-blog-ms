package az.shopery.blog_ms.mapper;

import az.shopery.blog_ms.model.dto.response.BlogResponseDto;
import az.shopery.blog_ms.model.dto.shared.AuthorDto;
import az.shopery.blog_ms.model.entity.BlogEntity;
import az.shopery.blog_ms.repository.BlogLikeRepository;
import az.shopery.blog_ms.util.common.FilenetClientHelper;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlogMapper {

    private final BlogLikeRepository blogLikeRepository;
    private final FilenetClientHelper filenetClientHelper;

    public BlogResponseDto toDto(BlogEntity blogEntity) {
        var blogResponseDto = BlogResponseDto.builder()
                .id(blogEntity.getId())
                .blogTitle(blogEntity.getBlogTitle())
                .content(blogEntity.getContent())
                .createdAt(blogEntity.getCreatedAt())
                .updatedAt(blogEntity.getUpdatedAt())
                .likeCount(blogLikeRepository.countByBlog(blogEntity))
                .author(AuthorDto.builder()
                        .name(blogEntity.getUser().getName())
                        .build()
                )
                .build();

        if (Objects.nonNull(blogEntity.getImageId())) {
            blogResponseDto.setImage(filenetClientHelper.getFile(blogEntity.getImageId()));
        }
        if (Objects.nonNull(blogEntity.getUser().getProfilePhotoId())) {
            var authorDto = AuthorDto.builder()
                    .name(blogEntity.getUser().getName())
                    .profilePhoto(filenetClientHelper.getFile(blogEntity.getUser().getProfilePhotoId()))
                    .build();
            blogResponseDto.setAuthor(authorDto);
        }

        return blogResponseDto;
    }
}
