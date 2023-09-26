package Journey_of_Taro_V3.Journey_of_Taro_V3.dtos;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

public class SongInputDto {

    @NotNull(message = "Songtitle is required")
    private String songTitle;
    @NotNull(message = "Artist name can't be blank")
    private  String artistName;
    @AssertTrue(message = "All favorited songs")
    private Boolean isFavorited;

    public SongInputDto(String songTitle, String artistName, Boolean isFavorited) {
        this.songTitle = songTitle;
        this.artistName = artistName;
        this.isFavorited = isFavorited;
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

    public Boolean getIsFavorited() {
        return isFavorited;
    }

    public void setIsFavorited(Boolean isFavorited) {
        this.isFavorited = isFavorited;
    }
}
