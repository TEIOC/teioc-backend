package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.entity.InternEntity;
import dev.astranfalio.teioc.repository.InternRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dumb")
@Slf4j
@AllArgsConstructor
public class DumbController {

    @Autowired
    private InternRepository internRepository;

    @GetMapping("hi")
    public void hi(){

        log.info("test");
    }
}
