package dev.astranfalio.teioc.dto;

import dev.astranfalio.teioc.entity.TopicEntity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TopicDto {

    private Integer id;

    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "Status cannot be null")
    private boolean status;

    @NotNull(message = "Order cannot be null")
    private Integer sortOrder;

    public static TopicDto convertToDto(TopicEntity topicEntity) {
        return TopicDto.builder()
                .id(topicEntity.getId())
                .name(topicEntity.getName())
                .status(topicEntity.isStatus())
                .sortOrder(topicEntity.getSortOrder())
                .build();
    }

    public static TopicEntity convertToEntity(TopicDto topicDto) {
        return TopicEntity.builder()
                .id(topicDto.getId())
                .name(topicDto.getName())
                .status(topicDto.isStatus())
                .sortOrder(topicDto.getSortOrder())
                .build();
    }
}

