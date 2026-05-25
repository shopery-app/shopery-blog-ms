package az.shopery.blog_ms.model.dto.response;

import az.shopery.model.dto.shared.DiscountDto;
import az.shopery.model.dto.shared.PriceHistoryDto;
import az.shopery.utils.enums.ProductCategory;
import az.shopery.utils.enums.ProductCondition;
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
public class ProductDetailResponseDto {
    UUID id;
    String productName;
    String description;
    String imageUrl;
    BigDecimal currentPrice;
    DiscountDto discountDto;
    Integer stockQuantity;
    ProductCategory category;
    ProductCondition condition;
    String shopName;
    UUID shopId;
    List<PriceHistoryDto> priceHistory;
    Instant createdAt;
}
