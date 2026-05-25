package az.shopery.blog_ms.model.dto.response.task;

import az.shopery.utils.enums.TicketStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SupportTicketResponseDto extends TaskResponseDto {
    String supportTicketSubject;
    String supportTicketDescription;
    TicketStatus ticketStatus;
}
