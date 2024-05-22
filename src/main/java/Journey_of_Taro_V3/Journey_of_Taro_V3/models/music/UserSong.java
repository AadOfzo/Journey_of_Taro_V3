package Journey_of_Taro_V3.Journey_of_Taro_V3.models.music;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UserSong extends Song{

    @Id
    private String songTitle;

    public UserSong() {
    }

    public UserSong(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getSongTitle() {
        return songTitle;
    }

}
