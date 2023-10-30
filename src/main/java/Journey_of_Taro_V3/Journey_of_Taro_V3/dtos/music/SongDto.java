package Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.users.User;

public class SongDto {
    private Long id;
    private String songTitle;
    private User artist;

    private Boolean isFavorited;
    private Long isFavoritedCounter;
    private Long playCounter;

    public SongDto() {
    }

    public SongDto(Long id, String songTitle, User artist, Boolean isFavorited, Long isFavoritedCounter, Long playCounter) {
        this.id = id;
        this.songTitle = songTitle;
        this.artist = artist;
        this.isFavorited = isFavorited;
        this.isFavoritedCounter = isFavoritedCounter;
        this.playCounter = playCounter;
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

    public User getArtist() {
        return artist;
    }

    public void setArtist(User artist) {
        this.artist = artist;
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
