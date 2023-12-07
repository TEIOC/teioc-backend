package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.config.JwtUtils;
import dev.astranfalio.teioc.dto.LoginDto;
import dev.astranfalio.teioc.service.AuthenticationService;
import dev.astranfalio.teioc.service.InternDetailsService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {

    private InternDetailsService internDetailsService;
    private final AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public String authenticate(@RequestBody LoginDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        final UserDetails intern = internDetailsService.loadUserByUsername(loginDto.getEmail());
        if (intern != null) {
            return ResponseEntity.ok(jwtUtils.generateToken(intern)).toString();
        }
        return ResponseEntity.badRequest().toString();
    }
}
