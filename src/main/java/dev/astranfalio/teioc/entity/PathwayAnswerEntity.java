package dev.astranfalio.teioc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name="pathway_answer")
@IdClass(PathwayAnswerId.class)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PathwayAnswerEntity {
    @Id
    @Column(name = "intern_id", nullable = false)
    private Integer internId;

    @Id
    @Column(name = "survey_id", nullable = false)
    private Integer surveyId;

    @Id
    @Column(name = "answer_id", nullable = false)
    private Integer answerId;

    @ManyToOne
    @JoinColumn(name = "intern_id", referencedColumnName = "id")
    private InternEntity intern;

    @ManyToOne
    @JoinColumn(name = "survey_id", referencedColumnName = "id")
    private SurveyEntity survey;

    @ManyToOne
    @JoinColumn(name = "answer_id", referencedColumnName = "id")
    private AnswerEntity answer;
}

// Composite key class
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
class PathwayAnswerId implements Serializable {
    private Integer internId;
    private Integer surveyId;
    private Integer answerId;
}
