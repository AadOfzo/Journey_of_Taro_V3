package Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.users;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.users.UserImage;

import java.util.Date;
import java.util.List;

public class UserDto {

    public Long id;
    public String username;
    public String password;
    public String apikey;

    public String firstname;
    public String lastname;
    public Date dateOfBirth;
    public String country;
    public String email;
    public UserImage userimage;

    public String artistname;
    public List<String> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    public String getApikey() {
        return apikey;
    }

    public String getEmail() {
        return email;
    }

    public String getArtistname() {
        return artistname;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public void setArtistname(String artistname) {
        this.artistname = artistname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }



    public UserImage getUserimage() {
        return userimage;
    }

    public void setUserimage(UserImage userimage) {
        this.userimage = userimage;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}