package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.dto.InternDto;
import dev.astranfalio.teioc.service.InternService;
import dev.astranfalio.teioc.entity.InternEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class InternController {
    private final InternService internService;

    @Autowired
    public InternController(InternService internService) {
        this.internService = internService;
    }
    @GetMapping
    @RequestMapping("/interns")
    public List<InternDto> getAllInterns() {
        return internService.findAll().stream()
                .map(InternDto::convertToDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    @RequestMapping("/postinterns")
    public ResponseEntity<InternDto> addIntern(@RequestBody InternDto internDto) {
        InternEntity internEntity = InternDto.convertToEntity(internDto);
        InternEntity savedEntity = internService.save(internEntity);
        InternDto savedDto = InternDto.convertToDto(savedEntity);
        return ResponseEntity.ok(savedDto);
    }

}