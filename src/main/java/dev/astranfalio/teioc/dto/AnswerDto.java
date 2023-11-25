package dev.astranfalio.teioc.dto;

import dev.astranfalio.teioc.entity.AnswerEntity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDto {

    private Integer id;

    @NotNull(message = "Label cannot be null")
    private String label;

    private Boolean status;
    private Integer sortOrder;
    private Integer questionId;

    public static AnswerDto convertToDto(AnswerEntity answerEntity) {
        return AnswerDto.builder()
                .id(answerEntity.getId())
                .label(answerEntity.getLabel())
                .status(answerEntity.getStatus())
                .sortOrder(answerEntity.getSortOrder())
                .questionId(answerEntity.getQuestion() != null ? answerEntity.getQuestion().getId() : null)
                .build();
    }
}
