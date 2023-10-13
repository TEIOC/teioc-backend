package dev.astranfalio.teioc.entity;

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
    private Integer intern_id;
    private Integer survey_id;
}
