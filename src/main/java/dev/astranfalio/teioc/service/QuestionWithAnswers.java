package dev.astranfalio.teioc.service;

import dev.astranfalio.teioc.entity.AnswerEntity;
import dev.astranfalio.teioc.entity.QuestionEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuestionWithAnswers {
    private QuestionEntity question;
    private List<AnswerEntity> incorrectAnswers;
    private AnswerEntity correctAnswer;
}
