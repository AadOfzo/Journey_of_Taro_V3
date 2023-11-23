package Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.Image;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.SongCollectionType;

import java.util.List;

public class SongCollectionDto {

    private Long id;
    private String songCollectionName;
    private List<Long> songIds; // Assuming you want to transfer only the IDs of the songs
    private Image collectionImage;
    private SongCollectionType songCollectionType;

    public SongCollectionDto() {
    }

    public SongCollectionDto(Long id, String songCollectionName, List<Long> songIds, Image collectionImage, SongCollectionType songCollectionType) {
        this.id = id;
        this.songCollectionName = songCollectionName;
        this.songIds = songIds;
        this.collectionImage = collectionImage;
        this.songCollectionType = songCollectionType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSongCollectionName() {
        return songCollectionName;
    }

    public void setSongCollectionName(String songCollectionName) {
        this.songCollectionName = songCollectionName;
    }

    public List<Long> getSongIds() {
        return songIds;
    }

    public void setSongIds(List<Long> songIds) {
        this.songIds = songIds;
    }

    public Image getCollectionImage() {
        return collectionImage;
    }

    public void setCollectionImage(Image collectionImage) {
        this.collectionImage = collectionImage;
    }

    public SongCollectionType getSongCollectionType() {
        return songCollectionType;
    }

    public void setSongCollectionType(SongCollectionType songCollectionType) {
        this.songCollectionType = songCollectionType;
    }
}

