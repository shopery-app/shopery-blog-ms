package az.shopery.blog_ms.model.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConversationResponseDto {
    UUID buyerId;
    String buyerName;
    ChatMessageResponseDto lastMessage;
}
