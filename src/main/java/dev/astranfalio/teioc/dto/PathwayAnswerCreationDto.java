package dev.astranfalio.teioc.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PathwayAnswerCreationDto {
    private Integer intern_id;
    private Integer survey_id;
    private Integer answer_id;
}
