package Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.security;

public class AuthenticationResponse {

    private final String jwt;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }


}