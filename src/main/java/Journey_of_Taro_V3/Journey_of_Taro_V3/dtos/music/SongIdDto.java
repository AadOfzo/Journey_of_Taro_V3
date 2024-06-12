package Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music;

public class SongIdDto {

    private Long id;
    private String songTitle;

    // Constructor
    public SongIdDto(Long id, String songTitle) {
        this.id = id;
        this.songTitle = songTitle;
    }

    // Getters and Setters
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
