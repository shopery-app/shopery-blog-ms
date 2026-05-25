package az.shopery.blog_ms.model.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StripeCheckoutResponseDto {
    String sessionId;
    String checkoutUrl;
}
