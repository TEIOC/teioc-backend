package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.entity.InternEntity;
import dev.astranfalio.teioc.repository.InternRepository;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RestController
public class InternController {

    @Autowired
    private InternRepository internRepository;

    @GetMapping("/interns")
    public List<InternEntity> getInterns() {
        return internRepository.findAll();
    }
}
