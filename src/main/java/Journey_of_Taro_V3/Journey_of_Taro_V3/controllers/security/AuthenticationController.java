package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.security;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.security.AuthenticationRequest;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.security.AuthenticationResponse;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.users.CustomUserDetailsService;
import Journey_of_Taro_V3.Journey_of_Taro_V3.utils.JwtUtil;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.logging.Logger;

@CrossOrigin
@RestController
public class AuthenticationController {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final JwtUtil jwtUtl;

    public AuthenticationController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtUtil jwtUtl) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtl = jwtUtl;
    }


    /*
        Deze methode geeft de principal (basis user gegevens) terug van de ingelogde gebruiker
    */
    @GetMapping(value = "/authenticated")
    public ResponseEntity<Object> authenticated(Authentication authentication, Principal principal) {
        return ResponseEntity.ok().body(principal);
    }


    /*
    Deze methode geeft het JWT token terug wanneer de gebruiker de juiste inloggegevens op geeft.
     */
    @PostMapping(value = "/authenticate")
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        Long userId = authenticationRequest.getUserId();
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();

        try {
            Authentication authentication = authenticate(userId, username, password);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwt = jwtUtl.generateToken(userDetails);

            return ResponseEntity.ok(new AuthenticationResponse(jwt));
        } catch (BadCredentialsException ex) {
            throw new Exception("Incorrect username or password", ex);
        }
    }

    private Authentication authenticate(Long userId, String username, String password) {
        if (userId != null) {
            // Authenticate using userId and password
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userId.toString(), password)
            );
        } else {
            // Authenticate using username and password
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        }
    }
}