package Journey_of_Taro_V3.Journey_of_Taro_V3.dtos;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

public class SongInputDto {

    @NotNull(message = "Songtitle is required")
    private String songtitle;
    @NotNull(message = "Artist name is required")
    private String artistname;
    @AssertTrue(message = "All favorited songs")
    private Boolean isfavorited;

    public SongInputDto(String songTitle, String artistName, boolean b) {
    }

    public String getSongtitle() {
        return songtitle;
    }

    public void setSongtitle(String songtitle) {
        this.songtitle = songtitle;
    }

    public String getArtistname() {
        return artistname;
    }

    public void setArtistname(String artistname) {
        this.artistname = artistname;
    }

    public Boolean getIsfavorited() {
        return isfavorited;
    }

    public void setIsfavorited(Boolean isfavorited) {
        this.isfavorited = isfavorited;
    }
}
