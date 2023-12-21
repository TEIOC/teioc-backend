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

public class QuestionEntity implements Activatable, Identifiable<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="label", nullable = false)
    private String label;

    @Builder.Default
    @Column(name = "status")
    private boolean status = false;

    @Column(name="sortOrder")
    private Integer sortOrder;

    @ManyToOne
    @JoinColumn(name="survey_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SurveyEntity survey;

    @ManyToOne
    @JoinColumn(name="correctAnswer_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AnswerEntity correctAnswer;

    @Override
    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }
}
