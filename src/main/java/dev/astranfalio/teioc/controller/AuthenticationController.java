package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.dto.LoginDto;
import dev.astranfalio.teioc.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {

    private AuthenticationService authenticationService;

    @PostMapping(value = "/login")
    @ResponseBody
    public Map<String, String> authenticate(@RequestBody LoginDto loginDto) {
        Map<String, String> tokenMap = authenticationService.authenticate(loginDto.getEmail(), loginDto.getPassword());
        return tokenMap;
    }
}
