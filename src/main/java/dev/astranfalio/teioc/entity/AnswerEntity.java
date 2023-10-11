package dev.astranfalio.teioc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="answer")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="label", nullable = false)
    private String label;

    @Column(name="status")
    private Boolean status;

    @Column(name="order")
    private Integer order;
}
