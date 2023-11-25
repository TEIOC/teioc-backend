package dev.astranfalio.teioc.dto;

import dev.astranfalio.teioc.entity.PathwayAnswerEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PathwayAnswerDto {
    private Integer intern_id;
    private Integer survey_id;
    private Integer answer_id;

    public static PathwayAnswerDto convertToDto(PathwayAnswerEntity pathwayAnswerEntity) {
        return PathwayAnswerDto.builder()
                .intern_id(pathwayAnswerEntity.getId().getIntern_id())
                .survey_id(pathwayAnswerEntity.getId().getSurvey_id())
                .answer_id(pathwayAnswerEntity.getId().getAnswer_id())
                .build();
    }
}

