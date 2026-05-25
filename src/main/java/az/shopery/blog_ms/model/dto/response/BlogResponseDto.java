package az.shopery.blog_ms.model.dto.response;

import az.shopery.blog_ms.model.dto.shared.AuthorDto;
import java.time.Instant;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
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
