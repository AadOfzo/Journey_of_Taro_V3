package Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.SongCollectionType;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

public class SongInputDto {

    @NotNull(message = "Song title is required")
    private String songTitle;

    @NotNull(message = "Artist name can't be blank")
    private String artistName;

    @NotNull(message = "Song collection type is required")
    private SongCollectionType songCollectionType;

    @AssertTrue(message = "All favorited songs")
    private Boolean isFavorited;

    @NotNull(message = "Favorited counter is required")
    private Long isFavoritedCounter;

    @NotNull(message = "Play counter is required")
    private Long playCounter;

    public SongInputDto(String songTitle, String artistName, SongCollectionType songCollectionType, Boolean isFavorited, Long isFavoritedCounter, Long playCounter) {
        this.songTitle = songTitle;
        this.artistName = artistName;
        this.songCollectionType = songCollectionType;
        this.isFavorited = isFavorited;
        this.isFavoritedCounter = isFavoritedCounter;
        this.playCounter = playCounter;
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

    public SongCollectionType getSongCollectionType() {
        return songCollectionType;
    }

    public void setSongCollectionType(SongCollectionType songCollectionType) {
        this.songCollectionType = songCollectionType;
    }

    public Boolean getIsFavorited() {
        return isFavorited;
    }

    public void setIsFavorited(Boolean isFavorited) {
        this.isFavorited = isFavorited;
    }

    public Long getIsFavoritedCounter() {
        return isFavoritedCounter;
    }

    public void setIsFavoritedCounter(Long isFavoritedCounter) {
        this.isFavoritedCounter = isFavoritedCounter;
    }

    public Long getPlayCounter() {
        return playCounter;
    }

    public void setPlayCounter(Long playCounter) {
        this.playCounter = playCounter;
    }
}
