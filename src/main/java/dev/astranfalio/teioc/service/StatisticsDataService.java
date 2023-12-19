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
import java.util.LinkedHashMap;


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

    public Double calculateOverallPerformance() {
        return pathwayRepository.calculateAverageScore() != null ? pathwayRepository.calculateAverageScore() : 0.0;
    }

    public Double calculateIndividualPerformance(Integer internId) {
        Double averageScore = pathwayRepository.calculateAverageScoreForIntern(internId);
        return averageScore != null ? averageScore : 0.0;
    }
// StatisticsDataService.java

    public Map<String, Map<String, Object>> calculateScoreAndDurationPerSurveyForIntern(Integer internId) {
        List<Object[]> scoreResults = surveyRepository.calculateScorePerSurveyForIntern(internId);
        List<PathwayEntity> durationResults = pathwayRepository.findDurationsBySurveyForIntern(internId);
        Map<String, Double> averageDurations = calculateAverageDurations(durationResults, false);

        return scoreResults.stream().collect(Collectors.toMap(
                result -> String.valueOf(result[1]), // Survey name
                result -> {
                    Map<String, Object> details = new HashMap<>();
                    details.put("Survey ID", (Integer) result[0]); // Include Survey ID
                    details.put("Average Score", (Double) result[2]);
                    details.put("Average Duration", averageDurations.getOrDefault(String.valueOf(result[1]), 0.0));
                    return details;
                }
        ));
    }

    public Map<String, Map<String, Object>> calculateScoreAndDurationPerTopicForIntern(Integer internId) {
        List<Object[]> scoreResults = surveyRepository.calculateScorePerTopicForIntern(internId);
        List<PathwayEntity> durationResults = pathwayRepository.findDurationsByTopicForIntern(internId);

        // We need to pass the topic name to the calculateAverageDurations function
        Map<String, Double> averageDurations = calculateAverageDurations(durationResults, true);

        return scoreResults.stream().collect(Collectors.toMap(
                result -> String.valueOf(result[1]),
                result -> {
                    Map<String, Object> details = new HashMap<>();
                    details.put("Topic ID", (Integer) result[0]);
                    details.put("Average Score", (Double) result[2]);
                    details.put("Average Duration", averageDurations.getOrDefault(String.valueOf(result[1]), 0.0));
                    return details;
                }
        ));
    }

    private Map<String, Double> calculateAverageDurations(List<PathwayEntity> durationResults, boolean isTopicWise) {
        Map<String, Long[]> durationSumsAndCounts = new HashMap<>();
        for (PathwayEntity pathway : durationResults) {
            String name = isTopicWise ? pathway.getSurvey().getTopic().getName() : pathway.getSurvey().getName();
            Time duration = pathway.getDuration();
            long durationInMinutes = duration != null ? duration.toLocalTime().getHour() * 60
                    + duration.toLocalTime().getMinute()
                    + duration.toLocalTime().getSecond() / 60L : 0;

            durationSumsAndCounts.compute(name, (key, value) -> {
                if (value == null) {
                    return new Long[]{durationInMinutes, 1L};
                } else {
                    value[0] += durationInMinutes;
                    value[1]++;
                    return value;
                }
            });
        }
        Map<String, Double> averageDurations = new HashMap<>();
        durationSumsAndCounts.forEach((name, sumAndCount) -> {
            double average = sumAndCount[0] / (double) sumAndCount[1];
            averageDurations.put(name, average);
        });
        return averageDurations;
    }

    public Map<Integer, Double> getInternRankingBySurvey(Integer surveyId) {
        List<Object[]> internData = surveyRepository.calculateRankingBySurvey(surveyId);
        return internData.stream()
                .sorted((a, b) -> Double.compare((Double) b[1], (Double) a[1]))
                .collect(Collectors.toMap(
                        data -> (Integer) data[0], // Intern ID
                        data -> (Double) data[1],  // Average Score
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    public Map<Integer, Double> getInternRankingByTopic(Integer topicId) {
        List<Object[]> internData = topicRepository.calculateRankingByTopic(topicId);
        return internData.stream()
                .sorted((a, b) -> Double.compare((Double) b[1], (Double) a[1]))
                .collect(Collectors.toMap(
                        data -> (Integer) data[0], // Intern ID
                        data -> (Double) data[1],  // Average Score
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    public Map<String, Double> getRankedSurveys() {
        List<Object[]> surveyData = surveyRepository.fetchSurveyDataForRanking();
        return surveyData.stream()
                .sorted((a, b) -> Double.compare((Double) b[1], (Double) a[1]))
                .collect(Collectors.toMap(
                        data -> (String) data[0], // Survey name
                        data -> (Double) data[1], // Average score
                        (e1, e2) -> e1,           // In case of duplicate keys, keep the first
                        LinkedHashMap::new        // Preserve the order
                ));
    }

    public Map<String, Double> getRankedTopics() {
        List<Object[]> topicData = topicRepository.fetchTopicDataForRanking();
        return topicData.stream()
                .sorted((a, b) -> Double.compare((Double) b[1], (Double) a[1]))
                .collect(Collectors.toMap(
                        data -> (String) data[0], // Topic name
                        data -> (Double) data[1], // Average score
                        (e1, e2) -> e1,           // In case of duplicate keys, keep the first
                        LinkedHashMap::new        // Preserve the order
                ));
    }

        /* public Map<String, Double> calculateActiveSurveyWisePerformance() {
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
    }  */

}
