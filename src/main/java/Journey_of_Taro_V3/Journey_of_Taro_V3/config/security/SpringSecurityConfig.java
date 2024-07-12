package Journey_of_Taro_V3.Journey_of_Taro_V3.config.security;

import Journey_of_Taro_V3.Journey_of_Taro_V3.filter.JwtRequestFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    public final UserDetailsService customUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;
    private final PasswordEncoder passwordEncoder;

    public SpringSecurityConfig(UserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter, PasswordEncoder passwordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
        this.passwordEncoder = passwordEncoder;
    }

    // Config AuthenticationManager met customUserDetailsService en passwordEncoder
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    // Config SecurityFilterChain met authorization en JWT filter
    @Bean
    protected SecurityFilterChain filter(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .cors(cors -> cors.and())
                .authorizeHttpRequests(authorize -> authorize
                        // Allow public access to specific endpoints
                        .requestMatchers(HttpMethod.POST, "/users").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/users/**").permitAll()

                        // Allow public access to file and image endpoints
                        .requestMatchers(HttpMethod.POST, "/fileUpload").permitAll()
                        .requestMatchers(HttpMethod.POST, "/uploads").permitAll()
                        .requestMatchers(HttpMethod.GET, "/uploads").permitAll()
                        .requestMatchers(HttpMethod.POST, "/images").permitAll()
                        .requestMatchers(HttpMethod.GET, "/images").permitAll()
                        .requestMatchers(HttpMethod.GET, "/images/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/images/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/images/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "uploads/images").permitAll()
                        .requestMatchers(HttpMethod.GET, "uploads/images/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/songs").permitAll()
                        .requestMatchers(HttpMethod.GET, "/songs/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "uploads/songs/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/songs/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/songs/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "uploads/songs/songCollections").permitAll()
                        .requestMatchers(HttpMethod.GET, "uploads/songs/songCollections").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "uploads/songs/songCollections").permitAll()
                        .requestMatchers(HttpMethod.POST, "/songCollections/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/songCollections/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/songCollections/**").permitAll()

                        // Allow public access to authentication endpoints
                        .requestMatchers("/authenticate").permitAll()
                        .requestMatchers("/authenticated").authenticated()

                        // Restrict access to all other endpoints
                        .anyRequest().denyAll()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Add JWT filter before UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
