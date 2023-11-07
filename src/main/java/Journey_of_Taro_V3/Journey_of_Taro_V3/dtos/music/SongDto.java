package Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.users.User;

public class SongDto {
    private Long id;
    private String songTitle;

    public SongDto() {
    }

    public SongDto(Long id, String songTitle) {
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
