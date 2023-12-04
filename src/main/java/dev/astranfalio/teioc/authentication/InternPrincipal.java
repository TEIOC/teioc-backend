package dev.astranfalio.teioc.authentication;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class InternPrincipal extends User {
    private Integer id;

    public InternPrincipal(Integer id, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        super(email, password, authorities);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
