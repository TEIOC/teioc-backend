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
@IdClass(PathwayId.class)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class PathwayEntity {
    @Id
    @Column(name = "intern_id", nullable = false)
    private Integer intern_id;

    @Id
    @Column(name = "survey_id", nullable = false)
    private Integer survey_id;

    @Column(name = "score")
    private Integer score;

    @Column(name = "duration")
    private Time duration;

    @ManyToOne
    @JoinColumn(name = "intern_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private InternEntity intern;

    @ManyToOne
    @JoinColumn(name = "survey_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SurveyEntity survey;
}
