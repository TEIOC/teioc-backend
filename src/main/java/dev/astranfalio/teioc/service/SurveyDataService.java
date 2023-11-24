package dev.astranfalio.teioc.service;

import dev.astranfalio.teioc.dto.SurveyDto;
import dev.astranfalio.teioc.entity.SurveyEntity;
import dev.astranfalio.teioc.entity.TopicEntity;
import dev.astranfalio.teioc.repository.SurveyRepository;
import dev.astranfalio.teioc.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.validation.Validator;

@Service
public class SurveyDataService extends AbstractDataService<SurveyEntity, Long, SurveyRepository> {

    private final TopicRepository topicRepository;

    @Autowired
    public SurveyDataService(SurveyRepository surveyRepository, TopicRepository topicRepository, Validator validator) {
        super(surveyRepository, validator);
        this.topicRepository = topicRepository;
    }

    public SurveyEntity activateSurvey(Long id) {
        SurveyEntity survey = findById(id);
        survey.setStatus(true);
        return save(survey);
    }

    public SurveyEntity deactivateSurvey(Long id) {
        SurveyEntity survey = findById(id);
        survey.setStatus(false);
        return save(survey);
    }

    public static SurveyEntity convertToEntity(SurveyDto surveyDto, TopicRepository topicRepository) {
        TopicEntity topic = surveyDto.getTopicId() != null
                ? topicRepository.findById(surveyDto.getTopicId()).orElse(null)
                : null;

        return SurveyEntity.builder()
                .id(surveyDto.getId())
                .status(surveyDto.getStatus())
                .sortOrder(surveyDto.getSortOrder())
                .topic(topic)
                .build();
    }
}