package dev.astranfalio.teioc.dto;

import dev.astranfalio.teioc.entity.QuestionEntity;
import dev.astranfalio.teioc.entity.SurveyEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {
    private Long id;

    private String label;
    private Boolean status;
    private Integer sortOrder;
    private Long surveyId;
    private Long correctAnswerId;

    public static QuestionDto convertToDto(QuestionEntity questionEntity) {
        return QuestionDto.builder()
                .id(questionEntity.getId())
                .label(questionEntity.getLabel())
                .status(questionEntity.getStatus())
                .sortOrder(questionEntity.getSortOrder())
                .surveyId(questionEntity.getSurvey() != null ? questionEntity.getSurvey().getId() : null)
                .correctAnswerId(questionEntity.getCorrectAnswer() != null ? questionEntity.getCorrectAnswer().getId() : null)
                .build();
    }
}
