package Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music;

import jakarta.validation.constraints.NotNull;

public class SongInputDto {

    @NotNull(message = "Song title is required")
    private String songTitle;

    public SongInputDto() {
    }

    public SongInputDto(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }
}
