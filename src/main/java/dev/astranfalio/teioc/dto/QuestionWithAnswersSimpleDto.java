package dev.astranfalio.teioc.dto;

import dev.astranfalio.teioc.service.QuestionWithAnswers;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class QuestionWithAnswersSimpleDto {

    @NotNull(message = "question field must be fulfilled.")
    private QuestionSimpleDto question;

    @NotNull(message = "incorrectAnswers field must be fulfilled.")
    private List<AnswerSimpleDto> incorrectAnswers;

    @NotNull(message = "correctAnswer field must be fulfilled.")
    private AnswerSimpleDto correctAnswer;

    public QuestionWithAnswers toModel() {
        return QuestionWithAnswers.builder()
                .question(question.toEntity())
                .incorrectAnswers(incorrectAnswers.stream().map(AnswerSimpleDto::toEntity).toList())
                .correctAnswer(correctAnswer.toEntity())
                .build();
    }
}
