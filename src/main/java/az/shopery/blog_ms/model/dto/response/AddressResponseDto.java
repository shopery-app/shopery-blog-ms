package az.shopery.blog_ms.model.dto.response;

import az.shopery.utils.enums.AddressType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressResponseDto {
    UUID id;
    String addressLine1;
    String addressLine2;
    String city;
    String country;
    String postalCode;
    boolean isDefault;
    AddressType addressType;
}
