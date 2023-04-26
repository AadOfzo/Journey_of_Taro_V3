package Journey_of_Taro_V3.Journey_of_Taro_V3.dtos;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.SongCollectionType;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class SongInputDto {

    @NotNull(message = "Songtitle is required")
    private String songtitle;
    @NotNull(message = "Artist name is required")
    private String artistname;
    @AssertTrue(message = "All favorited songs")
    private Boolean isfavorited;
    @NotNull(message = "Song collection must be added")
    private final SongCollectionType songCollectionType;

    public SongInputDto(String songtitle, String artistname, Boolean isfavorited, SongCollectionType songCollectionType) {
        this.songtitle = songtitle;
        this.artistname = artistname;
        this.isfavorited = isfavorited;
        this.songCollectionType = songCollectionType;
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

    public SongCollectionType getSongCollectionType() {
        return songCollectionType;
    }

}
