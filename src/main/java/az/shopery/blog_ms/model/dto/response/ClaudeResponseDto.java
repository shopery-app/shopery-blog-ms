package az.shopery.blog_ms.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClaudeResponseDto {
    String id;
    String type;
    String role;
    List<Content> content;
    String model;
    @JsonProperty("stop_reason")
    String stopReason;
    Usage usage;

    @Data
    public static class Content {
        String type;
        String text;
    }

    @Data
    public static class Usage {
        @JsonProperty("input_tokens")
        Integer inputTokens;
        @JsonProperty("output_tokens")
        Integer outputTokens;
    }
}
