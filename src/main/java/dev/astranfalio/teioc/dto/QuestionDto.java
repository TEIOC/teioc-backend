package dev.astranfalio.teioc.dto;

import dev.astranfalio.teioc.entity.QuestionEntity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {

    private Integer id;

    @NotNull(message = "Label cannot be null")
    private String label;

    private Boolean status;
    private Integer sortOrder;
    private Integer surveyId;
    private Integer correctAnswerId;

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

