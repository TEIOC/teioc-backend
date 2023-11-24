package dev.astranfalio.teioc.dto;

import dev.astranfalio.teioc.entity.InternEntity;
import dev.astranfalio.teioc.entity.PathwayEntity;
import dev.astranfalio.teioc.entity.SurveyEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Time;

@Data
@AllArgsConstructor
@Builder
public class PathwayDto {
    private Integer intern_id;
    private Integer survey_id;
    private Integer score;
    private Time duration;
    private InternEntity intern;
    private SurveyEntity survey;

    public static PathwayDto convertToPathwayDto(PathwayEntity pathwayEntity) {
        return PathwayDto.builder()
                .intern_id(pathwayEntity.getIntern_id())
                .survey_id(pathwayEntity.getSurvey_id())
                .score(pathwayEntity.getScore())
                .duration(pathwayEntity.getDuration())
                .intern(pathwayEntity.getIntern())
                .survey(pathwayEntity.getSurvey())
                .build();
    }
}
