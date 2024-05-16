package Journey_of_Taro_V3.Journey_of_Taro_V3.config.security;

import Journey_of_Taro_V3.Journey_of_Taro_V3.filter.JwtRequestFilter;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.images.ImageRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.users.CustomUserDetailsService;
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

    // Could not autowire UserDetailsService, JwtRequestFilter, PasswordEncoder dit zijn bekende errors en hebben geen gevolgen na reports op stackoverflow.
    public SpringSecurityConfig(UserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter, PasswordEncoder passwordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
        this.passwordEncoder = passwordEncoder;
    }

    // Authenticatie met customUserDetailsService en passwordEncoder
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder encoder, UserDetailsService udService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    // Authorisatie met jwt
    @Bean
    protected SecurityFilterChain filter (HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .httpBasic().disable()
                .cors().and()
                .authorizeHttpRequests()
                // Wanneer je deze uncomments, staat je hele security open. Je hebt dan alleen nog een jwt nodig.
//                .requestMatchers("/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/users").permitAll()
                .requestMatchers(HttpMethod.POST, "/users/**").permitAll()
//                .requestMatchers(HttpMethod.GET,"/users").authenticated()
                .requestMatchers(HttpMethod.GET,"/users").permitAll()
//                .requestMatchers(HttpMethod.POST,"/users/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")

                // RequestMatchers voor FILES
                .requestMatchers(HttpMethod.POST,"/fileUpload").permitAll()
                .requestMatchers(HttpMethod.POST,"/images").permitAll()
                .requestMatchers(HttpMethod.GET,"/images").permitAll()
                .requestMatchers(HttpMethod.GET,"/images/**").permitAll()
                .requestMatchers(HttpMethod.PUT,"/images/**").permitAll()
                .requestMatchers(HttpMethod.DELETE,"/images/**").permitAll()
                .requestMatchers(HttpMethod.GET,"/uploads/images/**").permitAll()
//                .requestMatchers(HttpMethod.GET, "/images").hasRole("USER")
//                .requestMatchers(HttpMethod.POST,"/images").hasRole("USER")
//                .requestMatchers(HttpMethod.GET, "/images").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.POST,"/images").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST,"/songs").permitAll()
                .requestMatchers(HttpMethod.GET,"/songs/**").permitAll()
                .requestMatchers(HttpMethod.PUT,"/songs/**").permitAll()
                .requestMatchers(HttpMethod.DELETE,"/songs/**").permitAll()
//                .requestMatchers(HttpMethod.GET,"/songs").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.POST,"/songs/**").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.DELETE, "/songs/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST,"/song-collections").permitAll()
//                .requestMatchers(HttpMethod.GET,"/songCollections").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.POST,"/songCollections/**").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.DELETE, "/songCollections/**").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.POST,"/songCollectionTypes").permitAll()
//                .requestMatchers(HttpMethod.GET,"/songCollectionTypes").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.POST,"/songCollectionTypes/**").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.DELETE, "/songCollectionTypes/**").hasRole("ADMIN").requestMatchers(HttpMethod.POST,"/songCollectionTypes").permitAll()
                // Je mag meerdere paths tegelijk definieren
//                .requestMatchers("/cimodules", "/remotecontrollers", "/televisions", "/wallbrackets").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/fileUpload", "/songs", "/images").permitAll()
//                .requestMatchers("/fileUpload", "/songs", "/images").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/authenticate").permitAll()
                .requestMatchers("/authenticated").authenticated()
                .anyRequest().denyAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


}