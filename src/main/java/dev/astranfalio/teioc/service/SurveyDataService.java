package dev.astranfalio.teioc.service;

import dev.astranfalio.teioc.dto.SurveyDto;
import dev.astranfalio.teioc.entity.SurveyEntity;
import dev.astranfalio.teioc.entity.TopicEntity;
import dev.astranfalio.teioc.repository.SurveyRepository;
import dev.astranfalio.teioc.repository.TopicRepository;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SurveyDataService extends AbstractDataService<SurveyEntity, Integer, SurveyRepository> {

    private final TopicRepository topicRepository;

    private final SurveyRepository surveyRepository;

    @Autowired
    public SurveyDataService(SurveyRepository surveyRepository, TopicRepository topicRepository, Validator validator, SurveyRepository surveyRepository1) {
        super(surveyRepository, validator);
        this.topicRepository = topicRepository;
        this.surveyRepository = surveyRepository1;
    }

    public SurveyEntity associateWithTopic(Integer survey_id, Integer topic_id) {
        SurveyEntity survey = findById(survey_id);
        TopicEntity topic = topicRepository.findById(topic_id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found with ID: " + topic_id));
        survey.setTopic(topic);
        return save(survey);
    }

    public Map<String, Double> calculateSurveyWisePerformance() {
        List<Object[]> results = surveyRepository.calculateAverageScorePerSurvey();
        return results.stream().collect(Collectors.toMap(
                result -> (String) result[0], // Survey name
                result -> (Double) result[1]  // Average score
        ));
    }

    public Map<String, Double> calculateTopicWisePerformance() {
        List<Object[]> results = surveyRepository.calculateAverageScorePerTopic();
        Map<String, Double> topicPerformance = new HashMap<>();
        for (Object[] result : results) {
            String topicName = (String) result[0];
            Double averageScore = (Double) result[1];
            topicPerformance.put(topicName, averageScore);
        }
        return topicPerformance;
    }

    public Map<String, Double> calculateTopicWisePerformanceForIntern(Integer internId) {
        List<Object[]> results = surveyRepository.calculateAverageScorePerTopicForIntern(internId);
        Map<String, Double> topicPerformance = new HashMap<>();
        for (Object[] result : results) {
            String topicName = (String) result[0];
            Double averageScore = (Double) result[1];
            topicPerformance.put(topicName, averageScore);
        }
        return topicPerformance;
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
}
