package dev.astranfalio.teioc.service;

import dev.astranfalio.teioc.config.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private InternDetailsService internDetailsService;
    private InternDataService internDataService;
    private final AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;

    public Map<String, String> authenticate(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        final UserDetails intern = internDetailsService.loadUserByUsername(email);
        String token = jwtUtils.generateToken(intern);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        internDataService.reviseLastConnection(intern.getUsername());
        return tokenMap;
    }
}
