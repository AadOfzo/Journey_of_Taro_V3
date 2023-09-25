package Journey_of_Taro_V3.Journey_of_Taro_V3.dtos;

public class SongDto {
    private Long id;
    public String songTitle;
    public String artistName;
    public Boolean isFavorited;

    public SongDto() {
    }

    public SongDto(Long id, String songTitle, String artistName, Boolean isFavorited) {
        this.id = id;
        this.songTitle = songTitle;
        this.artistName = artistName;
        this.isFavorited = isFavorited;
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

    public Boolean getIsFavorited() {
        return isFavorited;
    }

    public void setIsFavorited(Boolean isFavorited) {
        this.isFavorited = isFavorited;
    }

}
