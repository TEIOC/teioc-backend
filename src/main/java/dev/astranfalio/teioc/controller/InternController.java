package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.dto.InternDto;
import dev.astranfalio.teioc.entity.InternEntity;
import dev.astranfalio.teioc.service.InternDataService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/interns")
@AllArgsConstructor
public class InternController {

    private final InternDataService internDataService;

    //TODO : récupération mot de passe + envoi des mails
    @GetMapping
    @ResponseBody
    public List<InternDto> getAllInterns() {
        return internDataService.findAll().stream()
                .map(InternDto::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseBody
    public InternDto getInternById(@PathVariable Integer id) {
        InternEntity internEntity = internDataService.findById(id);
        InternDto internDto = InternDto.convertToDto(internEntity);
        return internDto;
    }

    @PostMapping
    @ResponseBody
    public InternDto addIntern(@Valid @RequestBody InternDto internDto) {
        InternEntity internEntity = InternDto.convertToEntity(internDto);
        InternEntity savedEntity = internDataService.save(internEntity);
        InternDto savedDto = InternDto.convertToDto(savedEntity);
        return savedDto;
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteIntern(@PathVariable Integer id) {
        internDataService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public InternDto updateIntern(@PathVariable Integer id, @Valid @RequestBody InternDto internDto) {
        InternEntity internEntity = InternDto.convertToEntity(internDto);
        InternEntity updatedEntity = internDataService.update(id, internEntity);
        InternDto updatedDto = InternDto.convertToDto(updatedEntity);
        return updatedDto;
    }

    @PutMapping("/{id}/activate")
    @ResponseBody
    public InternDto activateIntern(@PathVariable Integer id) {
        InternEntity internEntity = internDataService.activate(id);
        InternDto internDto = InternDto.convertToDto(internEntity);
        return internDto;
    }

    @PutMapping("/{id}/deactivate")
    @ResponseBody
    public InternDto deactivateIntern(@PathVariable Integer id) {
        InternEntity internEntity = internDataService.deactivate(id);
        InternDto internDto = InternDto.convertToDto(internEntity);
        return internDto;
    }
}
