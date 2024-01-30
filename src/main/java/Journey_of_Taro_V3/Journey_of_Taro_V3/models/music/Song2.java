package Journey_of_Taro_V3.Journey_of_Taro_V3.models.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.CustomMultipartFile;
import jakarta.persistence.*;

public class Song2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    CustomMultipartFile multipartFile;

    private String songTitle;

    public Song2() {
    }

    public Song2(CustomMultipartFile multipartFile, String songTitle) {
        this.multipartFile = multipartFile;
        this.songTitle = songTitle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomMultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(CustomMultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }
}
