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
    private Integer survey_id;
    private Integer correctAnswer_id;

    public static QuestionDto convertToDto(QuestionEntity questionEntity) {
        return QuestionDto.builder()
                .id(questionEntity.getId())
                .label(questionEntity.getLabel())
                .status(questionEntity.getStatus())
                .sortOrder(questionEntity.getSortOrder())
                .survey_id(questionEntity.getSurvey() != null ? questionEntity.getSurvey().getId() : null)
                .correctAnswer_id(questionEntity.getCorrectAnswer() != null ? questionEntity.getCorrectAnswer().getId() : null)
                .build();
    }
}

