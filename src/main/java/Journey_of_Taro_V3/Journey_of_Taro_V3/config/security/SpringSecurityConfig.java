package Journey_of_Taro_V3.Journey_of_Taro_V3.config.security;

import Journey_of_Taro_V3.Journey_of_Taro_V3.filter.JwtRequestFilter;
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

    public final CustomUserDetailsService customUserDetailsService;

    private final JwtRequestFilter jwtRequestFilter;
    private final PasswordEncoder passwordEncoder;

    public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter, PasswordEncoder passwordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
        this.passwordEncoder = passwordEncoder;
    }

    // PasswordEncoderBean. Deze kun je overal in je applicatie injecteren waar nodig.
    // Je kunt dit ook in een aparte configuratie klasse zetten.


    // Authenticatie met customUserDetailsService en passwordEncoder
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder encoder, UserDetailsService udService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }



    // Authorizatie met jwt
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
//                .requestMatchers(HttpMethod.GET,"/users").authenticated()
//                .requestMatchers(HttpMethod.POST,"/users/**").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
                // RequestMatchers voor Televisions, deze kunnen later verwijderd worden.
//                .requestMatchers(HttpMethod.POST, "/cimodules").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.DELETE, "/cimodules/**").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.POST, "/remotecontrollers").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.DELETE, "/remotecontrollers/**").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.POST, "/televisions").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.DELETE, "/televisions/**").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.POST, "/wallbrackets").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.DELETE, "/wallbrackets/**").hasRole("ADMIN")
                // RequestMatchers voor songs, songCollectionType, Music collection (Playlists)
                .requestMatchers(HttpMethod.POST,"/songs").permitAll()
                .requestMatchers(HttpMethod.POST,"/images").permitAll()
//                .requestMatchers(HttpMethod.GET,"/songs").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.POST,"/songs/**").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.DELETE, "/songs/**").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.POST,"/songCollections").permitAll()
//                .requestMatchers(HttpMethod.GET,"/songCollections").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.POST,"/songCollections/**").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.DELETE, "/songCollections/**").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.POST,"/songCollectionTypes").permitAll()
//                .requestMatchers(HttpMethod.GET,"/songCollectionTypes").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.POST,"/songCollectionTypes/**").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.DELETE, "/songCollectionTypes/**").hasRole("ADMIN").requestMatchers(HttpMethod.POST,"/songCollectionTypes").permitAll()
                // Je mag meerdere paths tegelijk definieren
//                .requestMatchers("/cimodules", "/remotecontrollers", "/televisions", "/wallbrackets").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/songs", "/images").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/authenticated").authenticated()
                .requestMatchers("/authenticate").permitAll()
                .anyRequest().denyAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}