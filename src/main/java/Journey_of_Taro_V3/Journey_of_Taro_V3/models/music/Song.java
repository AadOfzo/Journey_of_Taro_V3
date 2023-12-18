package Journey_of_Taro_V3.Journey_of_Taro_V3.models.music;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Entity
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue
    private Long id;

    @Lob
    private byte[] songData;

    private String songTitle;

    public Song() {
    }

    public Song(String songTitle, MultipartFile file) {
        this.songTitle = songTitle;
        try {
            this.songData = file.getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getSongData() {
        return songData;
    }

    public void setSongData(byte[] songData) {
        this.songData = songData;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }
}
