package dev.astranfalio.teioc.dto;

import dev.astranfalio.teioc.entity.QuestionEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionSimpleDto {
    private String label;

    public QuestionEntity toEntity() {
        return QuestionEntity.builder()
                .label(this.label)
                .sortOrder(0)
                .id(-1)
                .status(false)
                .build();
    }
}
