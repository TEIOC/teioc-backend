package dev.astranfalio.teioc.service;

import dev.astranfalio.teioc.entity.SurveyEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class SurveyQuestionAnswerModel {
    List<QuestionWithAnswers> questionWithAnswersList;
    SurveyEntity surveyEntity;
}
