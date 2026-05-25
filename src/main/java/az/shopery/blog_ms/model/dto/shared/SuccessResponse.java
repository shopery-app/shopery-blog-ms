package az.shopery.blog_ms.model.dto.shared;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessResponse<T> {
    @Builder.Default
    HttpStatus status = HttpStatus.OK;
    @Builder.Default
    int statusCode = HttpStatus.OK.value();
    @Builder.Default
    LocalDateTime timestamp = LocalDateTime.now();
    String message;
    T data;

    public static SuccessResponse<Void> of(String message) {
        return SuccessResponse.<Void>builder()
                .message(message)
                .build();
    }

    public static <T> SuccessResponse<T> of(T data, String message) {
        return SuccessResponse.<T>builder()
                .message(message)
                .data(data)
                .build();
    }
}
