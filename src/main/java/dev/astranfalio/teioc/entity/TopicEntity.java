package dev.astranfalio.teioc.entity;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "topic")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class TopicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "status", nullable = false)
    private boolean status;

    @Column(name = "sortOrder", nullable = false)
    private Long sortOrder;
}

