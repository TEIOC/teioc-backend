package dev.astranfalio.teioc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.sql.Time;
@Entity
@Table(name="pathway")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PathwayEntity {
    @EmbeddedId
    private PathwayId id;

    @Column(name = "score")
    private Integer score;

    @Column(name = "duration")
    private Time duration;

    @ManyToOne
    @MapsId("intern_id")
    @JoinColumn(name = "intern_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private InternEntity intern;

    @ManyToOne
    @MapsId("survey_id")
    @JoinColumn(name = "survey_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SurveyEntity survey;
}

