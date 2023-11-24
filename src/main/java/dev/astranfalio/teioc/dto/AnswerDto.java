package dev.astranfalio.teioc.dto;

import dev.astranfalio.teioc.entity.AnswerEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDto {
    private Long id;

    private String label;

    private Boolean status;

    private Integer sortOrder;

    private Long questionId;

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
