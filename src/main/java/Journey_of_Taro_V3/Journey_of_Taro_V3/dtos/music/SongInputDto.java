package Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public class SongInputDto {

    @NotNull(message = "Song title is required")
    private String songTitle;

    private MultipartFile file;

    public SongInputDto() {
    }

    public SongInputDto(String songTitle, MultipartFile file) {
        this.songTitle = songTitle;
        this.file = file;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
