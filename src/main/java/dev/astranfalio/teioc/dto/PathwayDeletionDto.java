package dev.astranfalio.teioc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PathwayDeletionDto {
    private Long intern_id;
    private Long survey_id;
}
