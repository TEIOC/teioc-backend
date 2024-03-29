package dev.astranfalio.teioc.dto;

import dev.astranfalio.teioc.entity.PathwayEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PathwayDto {
    private Integer intern_id;
    private Integer survey_id;
    private Integer score;
    private Time duration;

    public static PathwayDto convertToDto(PathwayEntity pathwayEntity) {
        Integer score = pathwayEntity.getScore() != null ? pathwayEntity.getScore() : 0;
        Time duration = pathwayEntity.getDuration() != null ? pathwayEntity.getDuration() : Time.valueOf("00:00:00");

        return PathwayDto.builder()
                .intern_id(pathwayEntity.getIntern() != null ? pathwayEntity.getIntern().getId() : null)
                .survey_id(pathwayEntity.getSurvey() != null ? pathwayEntity.getSurvey().getId() : null)
                .score(score)
                .duration(duration)
                .build();
    }
}
