package dev.astranfalio.teioc.service;

import dev.astranfalio.teioc.entity.PathwayEntity;
import dev.astranfalio.teioc.repository.PathwayRepository;
import dev.astranfalio.teioc.repository.SurveyRepository;
import dev.astranfalio.teioc.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
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
    public Map<String, Map<String, Object>> calculateScoreAndDurationPerSurveyForIntern(Integer internId) {
        List<Object[]> scoreResults = surveyRepository.calculateScorePerSurveyForIntern(internId);
        List<PathwayEntity> durationResults = pathwayRepository.findDurationsBySurveyForIntern(internId);

        Map<String, Double> averageDurations = calculateAverageDurations(durationResults);

        return scoreResults.stream().collect(Collectors.toMap(
                result -> (String) result[0],
                result -> {
                    Map<String, Object> details = new HashMap<>();
                    details.put("Average Score", (Double) result[1]);
                    details.put("Average Duration", averageDurations.getOrDefault((String) result[0], 0.0));
                    return details;
                }
        ));
    }

    public Map<String, Map<String, Object>> calculateScoreAndDurationPerTopicForIntern(Integer internId) {
        List<Object[]> scoreResults = surveyRepository.calculateScorePerTopicForIntern(internId);
        List<PathwayEntity> durationResults = pathwayRepository.findDurationsByTopicForIntern(internId);

        Map<String, Double> averageDurations = calculateAverageDurations(durationResults);

        return scoreResults.stream().collect(Collectors.toMap(
                result -> (String) result[0],
                result -> {
                    Map<String, Object> details = new HashMap<>();
                    details.put("Average Score", (Double) result[1]);
                    details.put("Average Duration", averageDurations.getOrDefault((String) result[0], 0.0));
                    return details;
                }
        ));
    }

    private Map<String, Double> calculateAverageDurations(List<PathwayEntity> durationResults) {
        // Map to store total durations and count for each survey/topic
        Map<String, Long[]> durationSumsAndCounts = new HashMap<>();

        for (PathwayEntity pathway : durationResults) {
            String name = pathway.getSurvey().getName(); // or getTopic().getName() based on your structure
            Time duration = pathway.getDuration();

            // Convert Time to minutes (or any other unit you prefer)
            long durationInMinutes = duration != null ? duration.toLocalTime().getHour() * 60
                    + duration.toLocalTime().getMinute()
                    + duration.toLocalTime().getSecond() / 60L : 0;

            // Update the total duration and count
            durationSumsAndCounts.compute(name, (key, value) -> {
                if (value == null) {
                    return new Long[]{durationInMinutes, 1L};
                } else {
                    value[0] += durationInMinutes; // Sum of durations
                    value[1]++; // Count
                    return value;
                }
            });
        }

        // Calculate average duration for each survey/topic
        Map<String, Double> averageDurations = new HashMap<>();
        durationSumsAndCounts.forEach((name, sumAndCount) -> {
            double average = sumAndCount[0] / (double) sumAndCount[1];
            averageDurations.put(name, average);
        });

        return averageDurations;
    }
}
