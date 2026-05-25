package az.shopery.blog_ms.model.dto.response;

import az.shopery.utils.enums.TicketStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserSupportTicketResponseDto {
    UUID id;
    String subject;
    String description;
    TicketStatus status;
    Instant createdAt;
    Instant updatedAt;
    String assignedTo;
}
