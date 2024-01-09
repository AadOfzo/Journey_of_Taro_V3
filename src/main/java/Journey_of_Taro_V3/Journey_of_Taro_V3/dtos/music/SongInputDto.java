package Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public class SongInputDto {

    @NotNull(message = "Song title is required")
    private String songTitle;

    private MultipartFile songFile;

    public SongInputDto() {
    }

    public SongInputDto(String songTitle, MultipartFile songFile) {
        this.songTitle = songTitle;
        this.songFile = songFile;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public MultipartFile getSongFile() {
        return songFile;
    }

    public void setSongFile(MultipartFile songFile) {
        this.songFile = songFile;
    }
}
