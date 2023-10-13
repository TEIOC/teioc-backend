package dev.astranfalio.teioc.entity;

import jakarta.persistence.*;
import lombok.*;
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
    private TopicEntity topic;
}
