package dev.astranfalio.teioc.dto;

import dev.astranfalio.teioc.entity.SurveyEntity;
import dev.astranfalio.teioc.service.SurveyQuestionAnswerModel;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SurveyCompleteSimpleDto {

    @NotNull(message = "Name cannot be null")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    private String name;
    private List<QuestionWithAnswersSimpleDto> questions;

    public SurveyQuestionAnswerModel toModel() {
        return SurveyQuestionAnswerModel.builder()
                .surveyEntity(
                        SurveyEntity.builder()
                                .name(name)
                                .id(-1)
                                .status(false)
                                .build()
                )
                .questionWithAnswersList(questions.stream()
                        .map(QuestionWithAnswersSimpleDto::toModel)
                        .toList())
                .build();
    }
}
