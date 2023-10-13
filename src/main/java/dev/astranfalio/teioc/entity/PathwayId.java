package dev.astranfalio.teioc.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PathwayId implements Serializable {

    @Column(name = "intern_id")
    private Long internId;

    @Column(name = "survey_id")
    private Long surveyId;
}
