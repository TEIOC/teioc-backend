package dev.astranfalio.teioc.service;

import dev.astranfalio.teioc.dto.LoginDto;
import dev.astranfalio.teioc.entity.InternEntity;
import dev.astranfalio.teioc.repository.InternRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private InternRepository internRepository;

    public InternEntity authenticate(LoginDto loginDto) {
        InternEntity intern = internRepository.findByEmail(loginDto.getEmail())
                .orElse(null); // This handles the case where findByEmail returns an Optional.

        if (intern != null && intern.getPassword().equals(loginDto.getPassword())) {
            return intern;
        } else {
            // Throwing a generic exception to avoid giving hints to potential attackers
            throw new RuntimeException("Invalid credentials");
        }
    }
}

