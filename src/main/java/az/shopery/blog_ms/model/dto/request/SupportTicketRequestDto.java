package az.shopery.blog_ms.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SupportTicketRequestDto {
    @NotBlank(message = "Subject cannot be blank!")
    @Size(min = 3, max = 40, message = "Support ticket subject must be between 3 and 40 characters.")
    String subject;
    @NotBlank(message = "Description cannot be blank!")
    @Size(max = 2000, message = "Maximum support ticket description length exceeded!")
    String description;
}
