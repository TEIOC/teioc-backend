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

    @GetMapping("/survey-performance")
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

}
