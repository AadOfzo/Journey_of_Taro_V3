package Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.Image;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.SongCollectionType;

import java.util.List;

public class SongCollectionInputDto {

    private String songCollectionName;
    private SongCollectionType songCollectionType;

    public String getSongCollectionName() {
        return songCollectionName;
    }

    public void setSongCollectionName(String songCollectionName) {
        this.songCollectionName = songCollectionName;
    }

    public SongCollectionType getSongCollectionType() {
        return songCollectionType;
    }

    public void setSongCollectionType(SongCollectionType songCollectionType) {
        this.songCollectionType = songCollectionType;
    }
}
