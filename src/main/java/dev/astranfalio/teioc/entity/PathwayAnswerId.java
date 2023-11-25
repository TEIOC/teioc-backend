package dev.astranfalio.teioc.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PathwayAnswerId implements Serializable {
    private Integer intern_id;
    private Integer survey_id;
    private Integer answer_id;
}

