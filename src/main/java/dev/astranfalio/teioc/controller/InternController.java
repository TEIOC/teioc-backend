package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.dto.InternDto;
import dev.astranfalio.teioc.service.InternService;
import dev.astranfalio.teioc.entity.InternEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;


@RestController
@RequestMapping("/interns")
public class InternController {
    private final InternService internService;

    @Autowired
    public InternController(InternService internService) {
        this.internService = internService;
    }
    @GetMapping
    public List<InternDto> getAllInterns() {
        return internService.findAll().stream()
                .map(InternDto::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InternDto> getInternById(@PathVariable Long id) {
        Optional<InternEntity> internEntityOptional = internService.findById(id);
        if (internEntityOptional.isPresent()) {
            InternDto internDto = InternDto.convertToDto(internEntityOptional.get());
            return ResponseEntity.ok(internDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<InternDto> addIntern(@RequestBody InternDto internDto) {
        InternEntity internEntity = InternDto.convertToEntity(internDto);
        InternEntity savedEntity = internService.save(internEntity);
        InternDto savedDto = InternDto.convertToDto(savedEntity);
        return ResponseEntity.ok(savedDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIntern(@PathVariable Long id) {
        internService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<InternDto> updateIntern(@PathVariable Long id, @RequestBody InternDto internDto) {
        InternEntity internEntity = InternDto.convertToEntity(internDto);
        InternEntity updatedEntity = internService.updateIntern(id, internEntity);
        InternDto updatedDto = InternDto.convertToDto(updatedEntity);
        return ResponseEntity.ok(updatedDto);
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<InternDto> activateIntern(@PathVariable Long id) {
        InternEntity internEntity = internService.activateIntern(id);
        InternDto internDto = InternDto.convertToDto(internEntity);
        return ResponseEntity.ok(internDto);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<InternDto> deactivateIntern(@PathVariable Long id) {
        InternEntity internEntity = internService.deactivateIntern(id);
        InternDto internDto = InternDto.convertToDto(internEntity);
        return ResponseEntity.ok(internDto);
    }
}