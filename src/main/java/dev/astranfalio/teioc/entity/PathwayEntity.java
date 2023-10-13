package dev.astranfalio.teioc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Entity
@Table(name="pathway")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PathwayEntity {

    @EmbeddedId
    private PathwayId pathwayId;

    private Integer score;

    private Time duration;
}
