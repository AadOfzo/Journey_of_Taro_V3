package Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.Image;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.Song;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.SongCollectionType;

import java.util.List;

//Todo ArrayList importeren met List van SongId's in In service SongCollection naar SongCollectionDto omzetten.

public class SongCollectionDto {

    private Long id;
    private List<SongIdDto> songIds;

    private String songCollectionTitle;


    public SongCollectionDto(Long id, List<SongIdDto> songIds) {
        this.id = id;
        this.songIds = songIds;
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
}
