package Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageDto;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class SongCollectionInputDto {
    @NotNull(message = "Please add a Title for your Song Collection")
    private String songCollectionTitle;
    private List<SongDto> songs;
    private ImageDto image;


//    public SongCollectionInputDto(String songCollectionTitle) {
//        this.songCollectionTitle = songCollectionTitle;
//    }
//
//    public String getSongCollectionTitle() {
//        return songCollectionTitle;
//    }
//
//    public void setSongCollectionTitle(String songCollectionTitle) {
//        this.songCollectionTitle = songCollectionTitle;
//    }


    public SongCollectionInputDto(String songCollectionTitle, List<SongDto> songs, ImageDto image) {
        this.songCollectionTitle = songCollectionTitle;
        this.songs = songs;
        this.image = image;
    }

    public String getSongCollectionTitle() {
        return songCollectionTitle;
    }

    public void setSongCollectionTitle(String songCollectionTitle) {
        this.songCollectionTitle = songCollectionTitle;
    }

    public List<SongDto> getSongs() {
        return songs;
    }

    public void setSongs(List<SongDto> songs) {
        this.songs = songs;
    }

    public ImageDto getImage() {
        return image;
    }

    public void setImage(ImageDto image) {
        this.image = image;
    }
}
