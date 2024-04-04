package Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.security;

public class AuthenticationRequest {

    private String username;
    private String password;
//      AuthenticationRequest wordt niet gepakt met een lege constructor (uit gecomment 26-10-23)
    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}