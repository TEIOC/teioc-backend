package dev.astranfalio.teioc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompletedSurveyDetailsDto {
    private Integer questionId;
    private String questionText;
    private Integer selectedAnswerId;
    private String selectedAnswerText;
    private Integer correctAnswerId;
    private String correctAnswerText;
}

