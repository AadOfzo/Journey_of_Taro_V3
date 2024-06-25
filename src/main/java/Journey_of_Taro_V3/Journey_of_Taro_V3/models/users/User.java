package Journey_of_Taro_V3.Journey_of_Taro_V3.models.users;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.UserImage;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.Song;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.UserSong;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.security.Authority;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    // User data:
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column
    private String apikey;

    // Personal fields:
    @Column(name = "firstname")
    private String firstName;
    @Column(name ="lastname")
    private String lastName;
    @Column(name = "dob")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    @Column
    private String country;
    @Column
    private String email;

    // User files:
    @JoinColumn(name = "userimage")
    @ManyToOne
    @JsonIgnoreProperties(value = {"contents","contentType"} )
    UserImage userImage;

    @JoinColumn(name = "usersong")
    @ManyToOne
    @JsonIgnoreProperties(value = {"contents","contentType"})
    UserSong userSong;

    @Column(name = "artistname",
            unique = true
    )
    private String artistName;
    @OneToMany(mappedBy = "artistName", cascade = CascadeType.ALL)
    private List<Song> songs;

    // Authority
    @OneToMany(
            targetEntity = Authority.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private Set<Authority> authorities = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserImage(UserImage userImage) {
        this.userImage = userImage;
    }

    public UserImage getUserImage() {
        return userImage;
    }

    public UserSong getUserSong() {
        return userSong;
    }

    public void setUserSong(UserSong userSong) {
        this.userSong = userSong;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public void addAuthorities(Authority authority) {
        authorities.add(authority);
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

}