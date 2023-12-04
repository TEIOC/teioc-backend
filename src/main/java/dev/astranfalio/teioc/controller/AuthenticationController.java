package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.authentication.JwtAuthenticationResponse;
import dev.astranfalio.teioc.authentication.JwtTokenProvider;
import dev.astranfalio.teioc.dto.LoginDto;
import dev.astranfalio.teioc.entity.InternEntity;
import dev.astranfalio.teioc.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        String jwt = jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
//        InternEntity intern = authenticationService.authenticate(loginDto);
//        return ResponseEntity.ok(intern);
    }
}
