package az.shopery.blog_ms.model.dto.response;

import az.shopery.utils.enums.MessageStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatMessageResponseDto {
    UUID id;
    UUID senderId;
    UUID receiverId;
    String content;
    MessageStatus status;
    Instant createdAt;
    Instant readAt;
}
