package az.shopery.blog_ms.model.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShopResponseDto {
    UUID id;
    String shopName;
    String description;
    Double rating;
    Instant createdAt;
    UUID sellerId;
    List<ProductResponseDto> products;
}
