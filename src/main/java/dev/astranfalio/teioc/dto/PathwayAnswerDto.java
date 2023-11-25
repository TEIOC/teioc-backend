package dev.astranfalio.teioc.dto;

import dev.astranfalio.teioc.entity.AnswerEntity;
import dev.astranfalio.teioc.entity.InternEntity;
import dev.astranfalio.teioc.entity.PathwayAnswerEntity;
import dev.astranfalio.teioc.entity.SurveyEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PathwayAnswerDto {
    private Integer intern_id;
    private Integer survey_id;
    private Integer answer_id;
    private InternEntity intern;
    private SurveyEntity survey;
    private AnswerEntity answer;

    public static PathwayAnswerDto convertToDto(PathwayAnswerEntity pathwayAnswerEntity) {
        return PathwayAnswerDto.builder()
                .intern_id(pathwayAnswerEntity.getIntern_id())
                .survey_id(pathwayAnswerEntity.getSurvey_id())
                .answer_id(pathwayAnswerEntity.getAnswer_id())
                .intern(pathwayAnswerEntity.getIntern())
                .survey(pathwayAnswerEntity.getSurvey())
                .answer(pathwayAnswerEntity.getAnswer())
                .build();
    }
}
