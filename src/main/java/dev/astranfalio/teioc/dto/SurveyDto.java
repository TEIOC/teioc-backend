package dev.astranfalio.teioc.dto;

import dev.astranfalio.teioc.entity.SurveyEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SurveyDto {

    private Integer id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    private String name;

    private Boolean status;
    private Integer sortOrder;
    private Integer topicId;

    public static SurveyDto convertToDto(SurveyEntity surveyEntity) {
        return SurveyDto.builder()
                .id(surveyEntity.getId())
                .name(surveyEntity.getName())
                .status(surveyEntity.getStatus())
                .sortOrder(surveyEntity.getSortOrder())
                .topicId(surveyEntity.getTopic() != null ? surveyEntity.getTopic().getId() : null)
                .build();
    }

}

