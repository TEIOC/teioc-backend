package dev.astranfalio.teioc.service;

import dev.astranfalio.teioc.repository.PathwayRepository;
import dev.astranfalio.teioc.repository.SurveyRepository;
import dev.astranfalio.teioc.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatisticsDataService {

    private final SurveyRepository surveyRepository;
    private final TopicRepository topicRepository;

    private final PathwayRepository pathwayRepository;

    @Autowired
    public StatisticsDataService(SurveyRepository surveyRepository, TopicRepository topicRepository, PathwayRepository pathwayRepository) {
        this.surveyRepository = surveyRepository;
        this.topicRepository = topicRepository;
        this.pathwayRepository = pathwayRepository;
    }

    public Map<String, Double> calculateActiveSurveyWisePerformance() {
        List<Object[]> results = surveyRepository.calculateAverageScorePerSurveyForActiveInterns();
        return results.stream().collect(Collectors.toMap(
                result -> (String) result[0],
                result -> (Double) result[1]
        ));
    }

    public Map<String, Double> calculateActiveTopicWisePerformance() {
        List<Object[]> results = surveyRepository.calculateAverageScorePerTopicForActiveInterns();
        Map<String, Double> topicPerformance = new HashMap<>();
        for (Object[] result : results) {
            String topicName = (String) result[0];
            Double averageScore = (Double) result[1];
            topicPerformance.put(topicName, averageScore);
        }
        return topicPerformance;
    }

    public Map<String, Double> calculateActiveTopicWisePerformanceForIntern(Integer internId) {
        List<Object[]> results = surveyRepository.calculateAverageScorePerTopicForActiveIntern(internId);
        Map<String, Double> topicPerformance = new HashMap<>();
        for (Object[] result : results) {
            String topicName = (String) result[0];
            Double averageScore = (Double) result[1];
            topicPerformance.put(topicName, averageScore);
        }
        return topicPerformance;
    }

    public Double calculateOverallPerformance() {
        return pathwayRepository.calculateAverageScore() != null ? pathwayRepository.calculateAverageScore() : 0.0;
    }

    public Double calculateIndividualPerformance(Integer internId) {
        Double averageScore = pathwayRepository.calculateAverageScoreForIntern(internId);
        return averageScore != null ? averageScore : 0.0;
    }
}
