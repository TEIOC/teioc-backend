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

public class SurveyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="status")
    private Boolean status;

    @Column(name="order")
    private Integer order;

    @ManyToOne
    @JoinColumn(name="topic_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TopicEntity topic;
}
