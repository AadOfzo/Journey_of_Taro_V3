package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.security;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.security.AuthenticationRequest;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.security.AuthenticationResponse;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.users.CustomUserDetailsService;
import Journey_of_Taro_V3.Journey_of_Taro_V3.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Principal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin
@RestController
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    /*
     * This method returns the principal (basic user information) of the logged-in user.
     */
    @GetMapping(value = "/authenticated")
    public ResponseEntity<Object> authenticated(Authentication authentication, Principal principal) {
        return ResponseEntity.ok().body(principal);
    }

    /*
     * This method returns the JWT token when the user provides the correct login credentials.
     */
    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();

        logger.info("Authentication attempt for user: {}", username);

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (BadCredentialsException ex) {
            logger.error("Incorrect username or password for user: {}", username);
            throw new Exception("Incorrect username or password", ex);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String jwt = jwtUtil.generateToken(userDetails);

        logger.info("Generated JWT token for user: {}", username);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
