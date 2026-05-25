package az.shopery.blog_ms.model.dto.response;

import az.shopery.model.dto.shared.DiscountDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponseDto {
    UUID id;
    String productName;
    String description;
    String imageUrl;
    BigDecimal currentPrice;
    Integer stockQuantity;
    DiscountDto discountDto;
}
