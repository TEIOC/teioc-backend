package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.config.JwtUtils;
import dev.astranfalio.teioc.dto.LoginDto;
import dev.astranfalio.teioc.service.InternDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {

    private InternDetailsService internDetailsService;
    private final AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;

    @PostMapping(value = "/login")
    @ResponseBody
    public Map<String, String> authenticate(@RequestBody LoginDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );
        final UserDetails intern = internDetailsService.loadUserByUsername(loginDto.getEmail());
        String token = jwtUtils.generateToken(intern);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        return tokenMap;
    }
}
