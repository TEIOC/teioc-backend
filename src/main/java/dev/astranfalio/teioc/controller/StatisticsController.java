package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.service.StatisticsDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    private final StatisticsDataService statisticsDataService;

    public StatisticsController(StatisticsDataService statisticsDataService) {
        this.statisticsDataService = statisticsDataService;
    }

    @GetMapping("/overall-performance")
    @ResponseBody
    public ResponseEntity<?> getOverallPerformance() {
        Double averageScore = statisticsDataService.calculateOverallPerformance();
        return ResponseEntity.ok(averageScore);
    }

    @GetMapping("/individual-performance/{internId}")
    @ResponseBody
    public ResponseEntity<?> getIndividualPerformance(@PathVariable Integer internId) {
        Double averageScore = statisticsDataService.calculateIndividualPerformance(internId);
        return ResponseEntity.ok(averageScore);
    }

    @GetMapping("/survey-performance/all")
    @ResponseBody
    public ResponseEntity<?> getSurveyPerformanceForAllInterns() {
        var allPerformances = statisticsDataService.calculateScoreAndDurationForAllInterns();
        return ResponseEntity.ok(allPerformances);
    }

    @GetMapping("/topic-performance/all")
    @ResponseBody
    public ResponseEntity<?> getTopicPerformanceForAllInterns() {
        var allPerformances = statisticsDataService.calculateScoreAndDurationForAllInternsForTopics();
        return ResponseEntity.ok(allPerformances);
    }

    @GetMapping("/survey-performance/intern/{internId}")
    @ResponseBody
    public ResponseEntity<?> getSurveyPerformanceForIntern(@PathVariable Integer internId) {
        var performance = statisticsDataService.calculateScoreAndDurationPerSurveyForIntern(internId);
        return ResponseEntity.ok(performance);
    }

    @GetMapping("/topic-performance/intern/{internId}")
    @ResponseBody
    public ResponseEntity<?> getTopicPerformanceForIntern(@PathVariable Integer internId) {
        var performance = statisticsDataService.calculateScoreAndDurationPerTopicForIntern(internId);
        return ResponseEntity.ok(performance);
    }

    @GetMapping("/ranking/survey/{surveyId}")
    public ResponseEntity<?> getInternRankingBySurvey(@PathVariable Integer surveyId) {
        Map<Integer, Double> ranking = statisticsDataService.getInternRankingBySurvey(surveyId);
        return ResponseEntity.ok(ranking);
    }

    @GetMapping("/ranking/topic/{topicId}")
    public ResponseEntity<?> getInternRankingByTopic(@PathVariable Integer topicId) {
        Map<Integer, Double> ranking = statisticsDataService.getInternRankingByTopic(topicId);
        return ResponseEntity.ok(ranking);
    }

    @GetMapping("/ranking/survey")
    public ResponseEntity<?> getSurveyRanking() {
        Map<String, Double> ranking = statisticsDataService.getRankedSurveys();
        return ResponseEntity.ok(ranking);
    }

    @GetMapping("/ranking/topic")
    public ResponseEntity<?> getTopicRanking() {
        Map<String, Double> ranking = statisticsDataService.getRankedTopics();
        return ResponseEntity.ok(ranking);
    }

    /* @GetMapping("/survey-performance")
    @ResponseBody
    public ResponseEntity<?> getActiveSurveyWisePerformance() {
        Map<String, Double> surveyPerformance = statisticsDataService.calculateActiveSurveyWisePerformance();
        return ResponseEntity.ok(surveyPerformance);
    }

    @GetMapping("/topic-performance")
    @ResponseBody
    public ResponseEntity<?> getActiveTopicWisePerformance() {
        Map<String, Double> topicPerformance = statisticsDataService.calculateActiveTopicWisePerformance();
        return ResponseEntity.ok(topicPerformance);
    }

    @GetMapping("/topic-performance/{internId}")
    @ResponseBody
    public ResponseEntity<?> getActiveTopicWisePerformanceForIntern(@PathVariable Integer internId) {
        Map<String, Double> topicPerformance = statisticsDataService.calculateActiveTopicWisePerformanceForIntern(internId);
        return ResponseEntity.ok(topicPerformance);
    } */

}
