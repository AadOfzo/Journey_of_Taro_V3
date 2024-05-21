package Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.CustomMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class SongInputDto {

    private MultipartFile songFile;
    private String songTitle;
    private String artistName;
    private String songUrl;

    // Constructor
    public SongInputDto() {

    }

//    public SongInputDto(MultipartFile songFile, String songTitle, String artistName, String songUrl) {
//        this.songFile = songFile;
//        this.songTitle = songTitle;
//        this.artistName = artistName;
//        this.songUrl = songUrl;
//    }

    // Getters and setters
    public MultipartFile getSongFile() {
        return songFile;
    }

    public void setSongFile(MultipartFile songFile) {
        this.songFile = songFile;
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

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }
}
