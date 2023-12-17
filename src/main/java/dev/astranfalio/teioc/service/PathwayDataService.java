package dev.astranfalio.teioc.service;

import dev.astranfalio.teioc.dto.PathwayDto;
import dev.astranfalio.teioc.entity.PathwayAnswerId;
import dev.astranfalio.teioc.entity.PathwayEntity;
import dev.astranfalio.teioc.entity.PathwayId;
import dev.astranfalio.teioc.entity.QuestionEntity;
import dev.astranfalio.teioc.repository.*;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.List;

@Service
public class PathwayDataService extends AbstractDataService<PathwayEntity, PathwayId, PathwayRepository> {

    private final PathwayRepository pathwayRepository;
    private final InternRepository internRepository;
    private final SurveyRepository surveyRepository;

    private final QuestionRepository questionRepository;
    private final PathwayAnswerRepository pathwayAnswerRepository ;

    @Autowired
    public PathwayDataService(PathwayRepository pathwayRepository,
                              InternRepository internRepository,
                              SurveyRepository surveyRepository,
                              PathwayAnswerRepository pathwayAnswerRepository,
                              Validator validator, QuestionRepository questionRepository) {
        super(pathwayRepository, validator);
        this.pathwayRepository = pathwayRepository;
        this.internRepository = internRepository;
        this.surveyRepository = surveyRepository;
        this.pathwayAnswerRepository = pathwayAnswerRepository;
        this.questionRepository = questionRepository;
    }

    public List<PathwayEntity> findAllByInternId(Integer internId) {
        return pathwayRepository.findAllByInternId(internId);
    }

    public void deleteById(PathwayId id) {
        pathwayAnswerRepository.deleteByInternIdAndSurveyId(id.getIntern_id(), id.getSurvey_id());
        repository.deleteById(id);
    }

    public PathwayDto addPathway(PathwayDto pathwayDto) {
        PathwayEntity pathwayEntity = convertToEntity(pathwayDto);

        // Calculate the score based on selected answers
        int score = calculateScore(pathwayDto.getIntern_id(), pathwayDto.getSurvey_id());
        pathwayEntity.setScore(score);

        // Set the duration from pathwayDto if it's provided and not null
        if (pathwayDto.getDuration() != null) {
            pathwayEntity.setDuration(pathwayDto.getDuration());
        }

        PathwayEntity savedEntity = pathwayRepository.save(pathwayEntity);
        return PathwayDto.convertToDto(savedEntity);
    }




    public PathwayEntity updatePathway(Integer internId, Integer surveyId, Time duration) {
        PathwayEntity pathway = findById(new PathwayId(internId, surveyId));
        int score = calculateScore(internId, surveyId);

        pathway.setDuration(duration); // Update duration
        pathway.setScore(score); // Update score

        return repository.save(pathway);
    }

    public void updatePathwayScore(Integer internId, Integer surveyId) {
        // Calculate the new score based on the updated pathway answers
        int score = calculateScore(internId, surveyId);

        // Find the pathway entity by internId and surveyId
        PathwayEntity pathway = findById(new PathwayId(internId, surveyId));

        // Update the score
        pathway.setScore(score);

        // Save the updated pathway entity
        repository.save(pathway);
    }


    private int calculateScore(Integer internId, Integer surveyId) {
        int score = 0;
        List<QuestionEntity> questions = questionRepository.findBySurveyId(surveyId);

        for (QuestionEntity question : questions) {
            Integer correctAnswerId = question.getCorrectAnswer().getId();
            PathwayAnswerId pathwayAnswerId = new PathwayAnswerId(internId, surveyId, correctAnswerId);

            if (pathwayAnswerRepository.existsById(pathwayAnswerId)) {
                score++;
            }
        }

        return score;
    }

    public Double calculateOverallPerformance() {
        Double averageScore = pathwayRepository.calculateAverageScore();
        return averageScore != null ? averageScore : 0.0;
    }

    public Double calculateIndividualPerformance(Integer internId) {
        Double averageScore = pathwayRepository.calculateAverageScoreForIntern(internId);
        return averageScore != null ? averageScore : 0.0;
    }
    public PathwayEntity convertToEntity(PathwayDto pathwayDto) {
        PathwayId pathwayId = new PathwayId(pathwayDto.getIntern_id(), pathwayDto.getSurvey_id());
        PathwayEntity pathwayEntity = new PathwayEntity();
        pathwayEntity.setId(pathwayId);
        pathwayEntity.setScore(pathwayDto.getScore());
        pathwayEntity.setDuration(pathwayDto.getDuration());
        pathwayEntity.setIntern(internRepository.findById(pathwayDto.getIntern_id()).orElseThrow(() -> new ResourceNotFoundException("Intern not found")));
        pathwayEntity.setSurvey(surveyRepository.findById(pathwayDto.getSurvey_id()).orElseThrow(() -> new ResourceNotFoundException("Survey not found")));
        return pathwayEntity;
    }


}

