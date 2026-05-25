package az.shopery.blog_ms.model.dto.response;

import az.shopery.model.dto.shared.ShopSummaryDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileResponseDto {
    UUID id;
    String firstName;
    String lastName;
    String email;
    String phone;
    LocalDate dateOfBirth;
    Instant createdAt;
    String profilePhotoUrl;
    ShopSummaryDto shop;
}
