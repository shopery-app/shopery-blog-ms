package az.shopery.blog_ms.model.dto.response.task;

import az.shopery.utils.enums.RequestStatus;
import az.shopery.utils.enums.SubscriptionTier;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShopCreationRequestResponseDto extends TaskResponseDto {
    String shopName;
    String shopDescription;
    SubscriptionTier subscriptionTier;
    String rejectionReason;
    RequestStatus requestStatus;
}
