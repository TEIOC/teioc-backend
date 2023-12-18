package dev.astranfalio.teioc.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="survey")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class SurveyEntity implements Activatable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="name")
    private String name;

    @Builder.Default
    @Column(name = "status")
    private boolean status = false;

    @Column(name="sortOrder")
    private Integer sortOrder;

    @ManyToOne
    @JoinColumn(name="topic_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TopicEntity topic;

    @Override
    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }
}
