package az.shopery.blog_ms.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogRequestDto {
    @NotBlank(message = "Title cannot be empty!")
    @Size(max = 40, message = "Title is too long.")
    String title;
    @NotBlank(message = "Content cannot be empty!")
    @Size(max = 400, message = "Content is too long.")
    String content;
}
