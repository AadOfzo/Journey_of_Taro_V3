package Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageDto;
import java.util.List;

public class SongCollectionDto {
    private Long id;
    private List<SongIdDto> songIds;
    private String songCollectionTitle;
    private ImageDto image;
    private String songCollectionUrl;  // Add this field

    public SongCollectionDto(Long id, List<SongIdDto> songIds, String songCollectionTitle, ImageDto image, String songCollectionUrl) {
        this.id = id;
        this.songIds = songIds;
        this.songCollectionTitle = songCollectionTitle;
        this.image = image;
        this.songCollectionUrl = songCollectionUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<SongIdDto> getSongIds() {
        return songIds;
    }

    public void setSongIds(List<SongIdDto> songIds) {
        this.songIds = songIds;
    }

    public String getSongCollectionTitle() {
        return songCollectionTitle;
    }

    public void setSongCollectionTitle(String songCollectionTitle) {
        this.songCollectionTitle = songCollectionTitle;
    }

    public ImageDto getImage() {
        return image;
    }

    public void setImage(ImageDto image) {
        this.image = image;
    }

    public String getSongCollectionUrl() {
        return songCollectionUrl;
    }

    public void setSongCollectionUrl(String songCollectionUrl) {
        this.songCollectionUrl = songCollectionUrl;
    }
}
