package az.shopery.blog_ms.model.dto.response.task;

import az.shopery.model.dto.shared.TaskCreatorDto;
import az.shopery.utils.enums.TaskCategory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskResponseDto {
    UUID id;
    TaskCategory taskCategory;
    TaskCreatorDto taskCreatorDto;
    Instant createdAt;
    Instant updatedAt;
}
