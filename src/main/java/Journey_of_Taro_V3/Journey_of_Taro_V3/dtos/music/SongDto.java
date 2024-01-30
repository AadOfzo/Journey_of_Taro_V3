package Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music;


public class SongDto {
    public Long id;
    public String songTitle;

    public String artistName;


    public SongDto() {
    }

    public SongDto(Long id, String songTitle, String artistName) {
        this.id = id;
        this.songTitle = songTitle;
        this.artistName = artistName;
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
