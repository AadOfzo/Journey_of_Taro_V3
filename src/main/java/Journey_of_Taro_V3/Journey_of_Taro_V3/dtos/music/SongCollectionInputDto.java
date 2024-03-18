package Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music;

import java.util.List;

public class SongCollectionInputDto {

    private List<Long> songIds;

    private String songCollectionTitle;

    public SongCollectionInputDto() {
    }

    public SongCollectionInputDto(List<Long> songIds) {
        this.songIds = songIds;
    }

    public List<Long> getSongIds() {
        return songIds;
    }

    public void setSongIds(List<Long> songIds) {
        this.songIds = songIds;
    }

    public String getSongCollectionTitle() {
        return songCollectionTitle;
    }

    public void setSongCollectionTitle(String songCollectionTitle) {
        this.songCollectionTitle = songCollectionTitle;
    }
}
