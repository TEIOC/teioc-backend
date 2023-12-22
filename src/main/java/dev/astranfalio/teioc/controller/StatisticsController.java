package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.service.StatisticsDataService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/statistics")
@AllArgsConstructor
public class StatisticsController {

    private final StatisticsDataService statisticsDataService;

    @GetMapping("/overall-performance")
    @ResponseBody
    public Double getOverallPerformance() {
        Double averageScore = statisticsDataService.calculateOverallPerformance();
        return averageScore;
    }

    @GetMapping("/individual-performance/{internId}")
    @ResponseBody
    public Double getIndividualPerformance(@PathVariable Integer internId) {
        Double averageScore = statisticsDataService.calculateIndividualPerformance(internId);
        return averageScore;
    }

    @GetMapping("/survey-performance/all")
    @ResponseBody
    public Map<Integer, Map<String, Map<String, Object>>> getSurveyPerformanceForAllInterns() {
        var allPerformances = statisticsDataService.calculateScoreAndDurationForAllInterns();
        return allPerformances;
    }

    @GetMapping("/topic-performance/all")
    @ResponseBody
    public Map<Integer, Map<String, Map<String, Object>>> getTopicPerformanceForAllInterns() {
        var allPerformances = statisticsDataService.calculateScoreAndDurationForAllInternsForTopics();
        return allPerformances;
    }

    @GetMapping("/survey-performance/intern/{internId}")
    @ResponseBody
    public Map<String, Map<String, Object>> getSurveyPerformanceForIntern(@PathVariable Integer internId) {
        var performance = statisticsDataService.calculateScoreAndDurationPerSurveyForIntern(internId);
        return performance;
    }

    @GetMapping("/topic-performance/intern/{internId}")
    @ResponseBody
    public Map<String, Map<String, Object>> getTopicPerformanceForIntern(@PathVariable Integer internId) {
        var performance = statisticsDataService.calculateScoreAndDurationPerTopicForIntern(internId);
        return performance;
    }

    @GetMapping("/ranking/survey/{surveyId}")
    @ResponseBody
    public Map<Integer, Double> getInternRankingBySurvey(@PathVariable Integer surveyId) {
        Map<Integer, Double> ranking = statisticsDataService.getInternRankingBySurvey(surveyId);
        return ranking;
    }

    @GetMapping("/ranking/topic/{topicId}")
    @ResponseBody
    public Map<Integer, Double> getInternRankingByTopic(@PathVariable Integer topicId) {
        Map<Integer, Double> ranking = statisticsDataService.getInternRankingByTopic(topicId);
        return ranking;
    }

    @GetMapping("/ranking/survey")
    @ResponseBody
    public Map<String, Double> getSurveyRanking() {
        Map<String, Double> ranking = statisticsDataService.getRankedSurveys();
        return ranking;
    }

    @GetMapping("/ranking/topic")
    @ResponseBody
    public Map<String, Double> getTopicRanking() {
        Map<String, Double> ranking = statisticsDataService.getRankedTopics();
        return ranking;
    }
}
