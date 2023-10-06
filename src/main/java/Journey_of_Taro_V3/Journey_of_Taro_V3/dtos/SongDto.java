package Journey_of_Taro_V3.Journey_of_Taro_V3.dtos;

public class SongDto {
    private Long id;
    private String songTitle;
    private String artistName;
    private Boolean isFavorited;
    private Long isFavoritedCounter;
    private Long playCounter;

    public SongDto() {
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
