package dev.astranfalio.teioc.service;

import dev.astranfalio.teioc.dto.LoginDto;
import dev.astranfalio.teioc.entity.InternEntity;
import dev.astranfalio.teioc.repository.InternRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private InternRepository internRepository;

    public InternEntity authenticate(LoginDto loginDto) {
        Optional<InternEntity> optionalIntern = internRepository.findByEmail(loginDto.getEmail());
        if (optionalIntern.isPresent()) {
            InternEntity intern = optionalIntern.get();
            if (intern.getPassword().equals(loginDto.getPassword())) {
                return intern;
            }
        }
        throw new RuntimeException("Invalid credentials");
    }
}

