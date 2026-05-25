package az.shopery.blog_ms.model.dto.response;

import az.shopery.utils.enums.SubscriptionTier;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserShopResponseDto {
    UUID id;
    String shopName;
    String description;
    BigDecimal totalIncome;
    Double rating;
    Instant createdAt;
    SubscriptionTier subscriptionTier;
}
