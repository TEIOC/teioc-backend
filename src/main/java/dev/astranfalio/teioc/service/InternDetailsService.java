package dev.astranfalio.teioc.service;

import dev.astranfalio.teioc.entity.InternEntity;
import dev.astranfalio.teioc.repository.InternRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InternDetailsService implements UserDetailsService {
    @Autowired
    private InternRepository internRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        InternEntity intern = internRepository.findByEmail(email);
        if (intern == null) {
            throw new ResourceNotFoundException("Utilisateur non trouvé avec l'e-mail: " + email);
        }
        return new User(intern.getEmail(), intern.getPassword(), getGrantedAuthorities("intern"));
    }

    private List<GrantedAuthority> getGrantedAuthorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return authorities;
    }
}
