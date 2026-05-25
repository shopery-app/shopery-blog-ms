package az.shopery.blog_ms.model.dto.response;

import az.shopery.utils.enums.OrderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponseDto {
    UUID id;
    UUID shopId;
    String shopName;
    OrderStatus status;
    BigDecimal totalPrice;
    String addressLine1;
    String addressLine2;
    String city;
    String country;
    String postalCode;
    Instant createdAt;
    List<OrderItemResponseDto> items;
}
