package az.shopery.blog_ms.model.dto.request;

import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemUpdateRequestDto {
    @Min(value = 1, message = "Quantity must be at least 1")
    int quantity;
}
