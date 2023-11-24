package dev.astranfalio.teioc.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="question")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class QuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="label", nullable = false)
    private String label;

    @Column(name="status")
    private Boolean status;

    @Column(name="order")
    private Integer order;

    @ManyToOne
    @JoinColumn(name="survey_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SurveyEntity survey;

    @ManyToOne
    @JoinColumn(name="correctAnswer_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AnswerEntity correctAnswer;
}
