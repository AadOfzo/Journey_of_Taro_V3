package Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.CustomMultipartFile;

public class SongInputDto {

    private CustomMultipartFile songFile;
    private String songTitle;
    private String artist;

    // Constructor
    public SongInputDto() {
    }

    // Getters and setters
    public CustomMultipartFile getSongFile() {
        return songFile;
    }

    public void setSongFile(CustomMultipartFile songFile) {
        this.songFile = songFile;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
