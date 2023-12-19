package dev.astranfalio.teioc.service;

import dev.astranfalio.teioc.dto.QuestionDto;
import dev.astranfalio.teioc.dto.SurveyDto;
import dev.astranfalio.teioc.entity.SurveyEntity;
import dev.astranfalio.teioc.entity.TopicEntity;
import dev.astranfalio.teioc.repository.SurveyRepository;
import dev.astranfalio.teioc.repository.TopicRepository;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurveyDataService extends AbstractDataService<SurveyEntity, Integer, SurveyRepository> {

    private final TopicRepository topicRepository;

    private final QuestionDataService questionDataService;

    @Autowired
    public SurveyDataService(SurveyRepository surveyRepository, TopicRepository topicRepository, Validator validator, QuestionDataService questionDataService, AnswerDataService answerDataService) {
        super(surveyRepository, validator);
        this.topicRepository = topicRepository;
        this.questionDataService = questionDataService;
    }

    public SurveyEntity associateWithTopic(Integer survey_id, Integer topic_id) {
        SurveyEntity survey = findById(survey_id);
        TopicEntity topic = topicRepository.findById(topic_id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found with ID: " + topic_id));
        survey.setTopic(topic);
        return save(survey);
    }

    public List<String> isSurveyValid(Integer id) {
        List<QuestionDto> questions = questionDataService.findBySurveyId(id);
        List<String> validationMessages = questionDataService.validateQuestions(questions);

        return validationMessages;
    }


    public static SurveyEntity convertToEntity(SurveyDto surveyDto, TopicRepository topicRepository) {
        TopicEntity topic = surveyDto.getTopicId() != null
                ? topicRepository.findById(surveyDto.getTopicId()).orElse(null)
                : null;

        return SurveyEntity.builder()
                .id(surveyDto.getId())
                .name(surveyDto.getName())
                .status(surveyDto.getStatus())
                .sortOrder(surveyDto.getSortOrder())
                .topic(topic)
                .build();
    }

    public boolean exists(Example<SurveyEntity> surveyEntityExample) {
        return repository.exists(surveyEntityExample);
    }
}
