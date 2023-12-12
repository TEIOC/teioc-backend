package dev.astranfalio.teioc.config;

import dev.astranfalio.teioc.service.InternDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final InternDetailsService internDetailsService;
    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader(AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            final String jwtToken = authHeader.substring(7);

            // Check if the JWT token string has two periods (standard JWT format)
            if (jwtToken.chars().filter(ch -> ch == '.').count() == 2) {
                try {
                    final String userEmail = jwtUtils.extractUsername(jwtToken);
                    if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        UserDetails userDetails = internDetailsService.loadUserByUsername(userEmail);
                        if (jwtUtils.isTokenValid(jwtToken, userDetails)) {
                            UsernamePasswordAuthenticationToken authToken =
                                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                            authToken.setDetails(userDetails);
                            SecurityContextHolder.getContext().setAuthentication(authToken);
                        }
                    }
                } catch (io.jsonwebtoken.MalformedJwtException e) {
                    // Log at a debug level since this can be a common case for guest users
                    logger.debug("Malformed JWT token encountered: " + jwtToken, e);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}

