package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.dto.InternDto;
import dev.astranfalio.teioc.entity.InternEntity;
import dev.astranfalio.teioc.service.InternDataService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/interns")
public class InternController {
    private final InternDataService internDataService;

    //TODO : récupération mot de passe + envoi des mails

    @Autowired
    public InternController(InternDataService internDataService) {
        this.internDataService = internDataService;
    }
    @GetMapping
    public List<InternDto> getAllInterns() {
        return internDataService.findAll().stream()
                .map(InternDto::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InternDto> getInternById(@PathVariable Integer id) {
        InternEntity internEntity = internDataService.findById(id);
        InternDto internDto = InternDto.convertToDto(internEntity);
        return ResponseEntity.ok(internDto);
    }

    @PostMapping
    public ResponseEntity<InternDto> addIntern(@Valid @RequestBody InternDto internDto) {
        InternEntity internEntity = InternDto.convertToEntity(internDto);
        InternEntity savedEntity = internDataService.save(internEntity);
        InternDto savedDto = InternDto.convertToDto(savedEntity);
        return ResponseEntity.ok(savedDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIntern(@PathVariable Integer id) {
        internDataService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<InternDto> updateIntern(@PathVariable Integer id, @Valid @RequestBody InternDto internDto) {
        InternEntity internEntity = InternDto.convertToEntity(internDto);
        InternEntity updatedEntity = internDataService.update(id, internEntity);
        InternDto updatedDto = InternDto.convertToDto(updatedEntity);
        return ResponseEntity.ok(updatedDto);
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<InternDto> activateIntern(@PathVariable Integer id) {
        InternEntity internEntity = internDataService.activate(id);
        InternDto internDto = InternDto.convertToDto(internEntity);
        return ResponseEntity.ok(internDto);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<InternDto> deactivateIntern(@PathVariable Integer id) {
        InternEntity internEntity = internDataService.deactivate(id);
        InternDto internDto = InternDto.convertToDto(internEntity);
        return ResponseEntity.ok(internDto);
    }
}