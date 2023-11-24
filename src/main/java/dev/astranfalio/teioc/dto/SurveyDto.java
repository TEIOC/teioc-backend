package dev.astranfalio.teioc.dto;

import dev.astranfalio.teioc.entity.SurveyEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SurveyDto {

    private Long id;
    private Boolean status;
    private Integer sortOrder;
    private Long topicId;

    public static SurveyDto convertToDto(SurveyEntity surveyEntity) {
        return SurveyDto.builder()
                .id(surveyEntity.getId())
                .status(surveyEntity.getStatus())
                .sortOrder(surveyEntity.getSortOrder())
                .topicId(surveyEntity.getTopic() != null ? surveyEntity.getTopic().getId() : null)
                .build();
    }

}

