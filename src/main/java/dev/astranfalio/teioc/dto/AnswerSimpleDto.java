package dev.astranfalio.teioc.dto;

import dev.astranfalio.teioc.entity.AnswerEntity;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerSimpleDto {
    private String label;
    private Integer sortOrder;

    public AnswerEntity toEntity() {
        return AnswerEntity.builder()
                .label(this.label)
                .sortOrder(this.sortOrder)
                .id(0)
                .status(false)
                .build();
    }
}
