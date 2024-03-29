package dev.astranfalio.teioc.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
@Entity
@Table(name="answer")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class AnswerEntity implements Activatable, Identifiable<Integer> {
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
    @JoinColumn(name="question_id", referencedColumnName = "id")
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private QuestionEntity question;

    @Override
    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }
}
