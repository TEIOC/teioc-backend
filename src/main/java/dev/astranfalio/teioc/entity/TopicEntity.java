package dev.astranfalio.teioc.entity;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "topic")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class TopicEntity implements Activatable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Builder.Default
    @Column(name = "status")
    private boolean status = false;

    @Column(name = "sortOrder", nullable = false)
    private Integer sortOrder;

    @Override
    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }
}

