package dev.astranfalio.teioc.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    private Integer intern_id;

    @Id
    @Column(name = "survey_id", nullable = false)
    private Integer survey_id;

    @Id
    @Column(name = "answer_id", nullable = false)
    private Integer answer_id;

    @ManyToOne
    @JoinColumn(name = "intern_id", referencedColumnName = "id", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private InternEntity intern;

    @ManyToOne
    @JoinColumn(name = "survey_id", referencedColumnName = "id", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SurveyEntity survey;

    @ManyToOne
    @JoinColumn(name = "answer_id", referencedColumnName = "id", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AnswerEntity answer;
}