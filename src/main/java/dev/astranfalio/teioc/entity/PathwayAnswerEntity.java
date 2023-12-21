package dev.astranfalio.teioc.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "pathway_answer")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PathwayAnswerEntity implements Identifiable<PathwayAnswerId> {

    @EmbeddedId
    private PathwayAnswerId id;

    @ManyToOne
    @MapsId("intern_id")
    @JoinColumn(name = "intern_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private InternEntity intern;

    @ManyToOne
    @MapsId("survey_id")
    @JoinColumn(name = "survey_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SurveyEntity survey;

    @ManyToOne
    @MapsId("answer_id")
    @JoinColumn(name = "answer_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AnswerEntity answer;
}