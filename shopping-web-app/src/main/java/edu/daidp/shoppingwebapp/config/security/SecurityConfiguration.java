package edu.daidp.shoppingwebapp.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private static final String[] WHITE_LIST_URL = {
            "/api/v1/auth/**",
            "/api/addresses/**",
            "/api/tracking/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"};
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                                               req.requestMatchers(WHITE_LIST_URL)
                                                       .permitAll()
//                                                       .requestMatchers("/api/carts/**","/api/orders/**")
//                                                       .hasAnyRole(CUSTOMER.name())
                                                       //.requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                                                       // .requestMatchers(HttpMethod.POST, "/api/products/**").hasAnyRole(ADMIN.name())
                                                       // .requestMatchers(HttpMethod.PUT, "/api/products/**").hasAnyRole(ADMIN.name())
                                                       //.requestMatchers(HttpMethod.DELETE, "/api/products/**").hasAnyRole(ADMIN.name())

                                                       //.requestMatchers(HttpMethod.GET, "/api/files/**").permitAll()
//                                                       .requestMatchers(HttpMethod.POST, "/api/files/**").hasAnyRole(ADMIN.name())
//                                                       .requestMatchers(HttpMethod.PUT, "/api/files/**").hasAnyRole(ADMIN.name())
//                                                       .requestMatchers(HttpMethod.DELETE, "/api/files/**").hasAnyRole(ADMIN.name())

//                                                       .requestMatchers(HttpMethod.GET, "/api/users").hasAnyRole(ADMIN.name())
//                                                       .requestMatchers(HttpMethod.POST, "/api/users").hasAnyRole(ADMIN.name())
//                                                       .requestMatchers(HttpMethod.PUT, "/api/users/**").hasAnyRole(ADMIN.name())
//                                                       .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasAnyRole(ADMIN.name())
                                                       .anyRequest()
                                                       .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                                logout.logoutUrl("/api/v1/auth/logout")
                                        .addLogoutHandler(logoutHandler)
                                        .logoutSuccessHandler(
                                                (request, response, authentication) -> SecurityContextHolder.clearContext())
                )
        ;

        return http.build();
    }
}
