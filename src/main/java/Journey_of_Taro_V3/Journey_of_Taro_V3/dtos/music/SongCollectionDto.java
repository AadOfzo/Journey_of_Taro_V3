package Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.Image;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.Song;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.SongCollectionType;

public class SongCollectionDto {

    private Long id;
    private String songCollectionTitle;
    private Song song;
    private Image image;
    private SongCollectionType songCollectionType;

    public SongCollectionDto() {
    }

    public SongCollectionDto(Long id, String songCollectionTitle, Song song, Image image, SongCollectionType songCollectionType) {
        this.id = id;
        this.songCollectionTitle = songCollectionTitle;
        this.song = song;
        this.image = image;
        this.songCollectionType = songCollectionType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSongCollectionTitle() {
        return songCollectionTitle;
    }

    public void setSongCollectionTitle(String songCollectionTitle) {
        this.songCollectionTitle = songCollectionTitle;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public SongCollectionType getSongCollectionType() {
        return songCollectionType;
    }

    public void setSongCollectionType(SongCollectionType songCollectionType) {
        this.songCollectionType = songCollectionType;
    }
}
