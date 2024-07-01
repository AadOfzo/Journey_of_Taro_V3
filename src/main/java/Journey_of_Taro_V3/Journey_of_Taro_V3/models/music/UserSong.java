package Journey_of_Taro_V3.Journey_of_Taro_V3.models.music;

import jakarta.persistence.*;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.users.User;

@Entity
@Table(name = "user_songs")
public class UserSong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String songTitle;
    private String songUrl;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public UserSong() {
    }

    public UserSong(String songTitle, String songUrl) {
        this.songTitle = songTitle;
        this.songUrl = songUrl;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
