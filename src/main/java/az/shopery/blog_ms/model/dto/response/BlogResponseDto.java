package az.shopery.blog_ms.model.dto.response;

import az.shopery.model.dto.shared.AuthorDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class BlogResponseDto {
    UUID id;
    String blogTitle;
    String content;
    Instant createdAt;
    Instant updatedAt;
    String imageUrl;
    Integer likeCount;
    AuthorDto author;
}
