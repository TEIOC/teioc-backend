package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.dto.LoginDto;
import dev.astranfalio.teioc.entity.InternEntity;
import dev.astranfalio.teioc.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<InternEntity> login(@RequestBody LoginDto loginDto) {
        InternEntity intern = authenticationService.authenticate(loginDto);
        return ResponseEntity.ok(intern);
    }
}
