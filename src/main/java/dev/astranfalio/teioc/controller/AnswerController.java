package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.dto.AnswerDto;
import dev.astranfalio.teioc.entity.AnswerEntity;
import dev.astranfalio.teioc.service.AnswerDataService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/answers")
@AllArgsConstructor
public class AnswerController {

    private final AnswerDataService answerDataService;

    @GetMapping
    @ResponseBody
    public List<AnswerDto> getAllAnswers() {
        return answerDataService.findAll().stream()
                .map(AnswerDto::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseBody
    public AnswerDto getAnswerById(@PathVariable Integer id) {
        AnswerEntity answerEntity = answerDataService.findById(id);
        AnswerDto answerDto = AnswerDto.convertToDto(answerEntity);
        return answerDto;
    }

    @GetMapping("/questions/{id}")
    @ResponseBody
    public List<AnswerDto> getAnswersByQuestionId(@PathVariable Integer id) {
        return answerDataService.findAnswersByQuestionId(id);
    }


    @PostMapping
    @ResponseBody
    public AnswerDto addAnswer(@Valid @RequestBody AnswerDto answerDto) {
        AnswerEntity answerEntity = AnswerDataService.convertToEntity(answerDto, answerDataService.getQuestionRepository());
        AnswerEntity savedEntity = answerDataService.save(answerEntity);
        AnswerDto savedDto = AnswerDto.convertToDto(savedEntity);
        return savedDto;
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteAnswer(@PathVariable Integer id) {
        answerDataService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public AnswerDto updateAnswer(@PathVariable Integer id, @Valid @RequestBody AnswerDto answerDto) {
        AnswerEntity answerEntity = AnswerDataService.convertToEntity(answerDto, answerDataService.getQuestionRepository());
        AnswerEntity updatedEntity = answerDataService.update(id, answerEntity);
        AnswerDto updatedDto = AnswerDto.convertToDto(updatedEntity);
        return updatedDto;
    }

    @PutMapping("/{id}/activate")
    @ResponseBody
    public AnswerDto activateAnswer(@PathVariable Integer id) {
        AnswerEntity answerEntity = answerDataService.activate(id);
        AnswerDto answerDto = AnswerDto.convertToDto(answerEntity);
        return answerDto;
    }

    @PutMapping("/{id}/deactivate")
    @ResponseBody
    public AnswerDto deactivateAnswer(@PathVariable Integer id) {
        AnswerEntity answerEntity = answerDataService.deactivate(id);
        AnswerDto answerDto = AnswerDto.convertToDto(answerEntity);
        return answerDto;
    }
}
