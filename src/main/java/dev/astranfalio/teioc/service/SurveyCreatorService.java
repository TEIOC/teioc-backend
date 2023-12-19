package dev.astranfalio.teioc.service;

import dev.astranfalio.teioc.dto.QuestionSimpleDto;
import dev.astranfalio.teioc.dto.QuestionWithAnswersSimpleDto;
import dev.astranfalio.teioc.dto.SurveyCompleteSimpleDto;
import dev.astranfalio.teioc.entity.AnswerEntity;
import dev.astranfalio.teioc.entity.QuestionEntity;
import dev.astranfalio.teioc.entity.SurveyEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class SurveyCreatorService {

    QuestionDataService questionDataService;
    SurveyDataService surveyDataService;
    AnswerDataService answerDataService;

    public void createQuestionWithAnswers(QuestionWithAnswersSimpleDto dto) {
        QuestionWithAnswers questionWithAnswers = dto.toModel();
        if (isQuestionWithAnswersValid(questionWithAnswers)) {
            saveQuestionWithAnswers(questionWithAnswers);
        }
    }

    private void saveQuestionWithAnswers(QuestionWithAnswers questionWithAnswers) {
        QuestionEntity savedQuestion =
                questionDataService.save(questionWithAnswers.getQuestion());
        for (AnswerEntity answerEntity : questionWithAnswers.getIncorrectAnswers()) {
            answerEntity.setQuestion(savedQuestion);
            answerDataService.save(answerEntity);
        }
        AnswerEntity correctAnswer = questionWithAnswers.getCorrectAnswer();
        correctAnswer.setQuestion(savedQuestion);
        AnswerEntity savedCorrectAnswer = answerDataService.save(correctAnswer);
        questionDataService.associateWithCorrectAnswer(
                savedQuestion.getId(),
                savedCorrectAnswer.getId()
        );
    }

    public boolean isQuestionWithAnswersValid(QuestionWithAnswers input) {
        if (
                input.getQuestion() == null
                || input.getQuestion().getLabel() == null
                || input.getQuestion().getLabel().isEmpty()
                || input.getCorrectAnswer() == null
                || input.getCorrectAnswer().getLabel() == null
                || input.getCorrectAnswer().getLabel().isEmpty()
                || input.getIncorrectAnswers() == null
                || input.getIncorrectAnswers().isEmpty()
        ) {
            return false;
        }

        Set<Integer> takenSortOrder = new HashSet<>();
        takenSortOrder.add(input.getCorrectAnswer().getSortOrder());
        for (AnswerEntity answer : input.getIncorrectAnswers()) {
            if (answer.getLabel() == null || answer.getLabel().isEmpty()) {
                return false;
            }
            if (takenSortOrder.contains(answer.getSortOrder())) {
                return false;
            }
            takenSortOrder.add(answer.getSortOrder());
        }
        return true;
    }

    public void createEmptyQuestion(QuestionSimpleDto dto) {
        QuestionEntity questionEntity = dto.toEntity();
        if (isQuestionValidForCreation(questionEntity)) {
            questionDataService.save(questionEntity);
        }
    }

    public boolean isQuestionValidForCreation(QuestionEntity questionEntity) {
        return questionEntity != null
                && questionEntity.getLabel() != null
                && !questionEntity.getLabel().isEmpty()
                && !questionDataService.existsById(questionEntity.getId());
    }

    public void createSurveyWithQuestionWithAnswers(SurveyCompleteSimpleDto dto) {
        SurveyQuestionAnswerModel surveyQAModel = dto.toModel();
        if (isSurveyQAValidForCreation(surveyQAModel)) {
            SurveyEntity savedSurvey = surveyDataService.save(surveyQAModel.getSurveyEntity());
            for (QuestionWithAnswers questionWithAnswers : surveyQAModel.getQuestionWithAnswersList()) {
                QuestionEntity question = questionWithAnswers.getQuestion();
                question.setSurvey(savedSurvey);
                saveQuestionWithAnswers(questionWithAnswers);
            }
        }
    }

    public boolean isSurveyQAValidForCreation(SurveyQuestionAnswerModel input) {
        return input.getSurveyEntity().getName() != null
                && !input.getSurveyEntity().getName().isEmpty()
                && !surveyDataService.exists(Example.of(input.getSurveyEntity()))
                && input.getQuestionWithAnswersList().stream().allMatch(this::isQuestionWithAnswersValid);
    }
}
