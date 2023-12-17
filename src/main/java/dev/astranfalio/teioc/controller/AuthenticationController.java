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

    private Map<String, String> generateToken(UserDetails intern) {
        String token = jwtUtils.generateToken(intern);
        String refreshToken = jwtUtils.generateRefreshToken(intern); // Generate refresh token

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("refreshToken", refreshToken); // Include refresh token in the response
        return tokenMap;
    }

    @PostMapping("/login")
    @ResponseBody
    public Map<String, String> authenticate(@RequestBody LoginDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );
        final UserDetails intern = internDetailsService.loadUserByUsername(loginDto.getEmail());
        return generateToken(intern);
    }
    @PostMapping("/refresh-token")
    @ResponseBody
    public Map<String, String> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        if (!jwtUtils.validateRefreshToken(refreshToken)) {
            throw new RuntimeException("Invalid Refresh Token");
        }

        String username = jwtUtils.extractUsername(refreshToken);
        final UserDetails intern = internDetailsService.loadUserByUsername(username);

        return generateToken(intern);
    }
}
