package Journey_of_Taro_V3.Journey_of_Taro_V3.models.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.users.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue
    private Long id;

    private String songTitle;

    public Song() {
    }

    public Song(Long id, String songTitle) {
        this.id = id;
        this.songTitle = songTitle;
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

    }

