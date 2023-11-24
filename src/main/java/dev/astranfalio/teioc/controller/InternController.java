package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.dto.InternDto;
import dev.astranfalio.teioc.service.InternService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}