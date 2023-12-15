// In a new file QuestionWithAnswersDto.java
package dev.astranfalio.teioc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionWithAnswersDto {

    private Integer id;
    private String label;
    private List<AnswerDto> answers;

}

