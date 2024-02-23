package Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.Song;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.users.User;

public class SongDto {
    private Long id;
    private String songTitle;
    private String artistName;

    public SongDto() {
    }

    public SongDto(Long id, String songTitle, User artist) {
        this.id = id;
        this.songTitle = songTitle;
        this.artistName = artist.getUsername(); // Assuming you want to use the username as the artist name
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

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
}
