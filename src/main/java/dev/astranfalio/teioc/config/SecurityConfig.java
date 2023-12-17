package dev.astranfalio.teioc.config;

import dev.astranfalio.teioc.service.InternDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    public static final String INTERN = "INTERN";
    private final JwtAuthFilter jwtAuthFilter;
    private final InternDetailsService internDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // PUBLIC ROUTES
                        .requestMatchers(HttpMethod.GET, "/interns", "/interns/*", "/surveys", "/topics").permitAll()
                        .requestMatchers(HttpMethod.POST, "/interns", "/auth/login",
                                "/auth/logout", "/interns/reset-password",
                                "/email/reset-password", "/email/activate").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/interns/*/activate").permitAll()
                        // PROTECTED ROUTES - INTERN ROLE
                        .requestMatchers(HttpMethod.GET, "/questions",
                                "/questions/*", "/questions/topics/*", "/questions/surveys/*",
                                "/answers", "/answers/*", "/answers/questions/*",
                                "/pathways", "/pathways/*/*", "/pathways/intern/*",
                                "/surveys/*", "/topics/*", "/pathwayanswers",
                                "/pathwayanswers/*/*", "/pathways/statistics/*", "/surveys/statistics/*").hasRole(INTERN)
                        .requestMatchers(HttpMethod.POST, "/pathwayanswers").hasRole(INTERN)
                        .requestMatchers(HttpMethod.PUT, "/interns/*/deactivate", "/pathwayanswers/*/*/*").hasRole(INTERN)
                        .requestMatchers( HttpMethod.DELETE, "/pathwayanswers/*/*/*").hasRole(INTERN)
                        // ADMIN FULL ACCESS (Not implemented yet)
//                        .anyRequest().hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
//                .formLogin(form -> form.loginProcessingUrl("/login").permitAll())
                .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout")).permitAll())
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

   @Bean
    public AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(internDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
//        return new BCryptPasswordEncoder();
    }
}





