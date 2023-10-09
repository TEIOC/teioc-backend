package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.entity.InternEntity;
import dev.astranfalio.teioc.repository.InternRepository;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@RestController
public class InternController {

    @Autowired
    private InternRepository internRepository;

    @GetMapping("/interns")
    public List<InternEntity> getInterns() {
        return internRepository.findAll();
    }

    @GetMapping("/do")
    public void save() {
        InternEntity internEntity = InternEntity.builder()
                .email("testemail2.intern@example.com")
                .company("testcompany2.intern")
                .status(false)
//                .id(2L)
                .contactDetails("testcontactDetails2.intern")
                .creationDate(Date.valueOf(LocalDate.now()))
                .password("testpassword2.intern")
                .build();

        internRepository.save(internEntity);
    }
}
