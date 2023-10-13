package dev.astranfalio.teioc.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PathwayAnswerId implements Serializable {
    private Integer internId;
    private Integer surveyId;
    private Integer answerId;
}

