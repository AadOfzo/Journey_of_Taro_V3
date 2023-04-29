package Journey_of_Taro_V3.Journey_of_Taro_V3.dtos;

import java.util.ArrayList;
import java.util.List;

public class SongCollectionDto {
    private Long id;
    private String collectionname;
    private List<SongDto> songDto;
    private SongCollectionTypeDto songCollectionType;
    private List<SongDto> songs = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCollectionname() {
        return collectionname;
    }

    public void setCollectionname(String collectionname) {
        this.collectionname = collectionname;
    }

    public List<SongDto> getSongDto() {
        return songDto;
    }

    public void setSongDto(List<SongDto> songDto) {
        this.songDto = songDto;
    }

    public SongCollectionTypeDto getSongCollectionType() {
        return songCollectionType;
    }

    public void setSongCollectionType(SongCollectionTypeDto songCollectionType) {
        this.songCollectionType = songCollectionType;
    }

    public List<SongDto> getSongs() {
        return songs;
    }

    public void setSongs(List<SongDto> songs) {
        this.songs = songs;
    }
}
