package Journey_of_Taro_V3.Journey_of_Taro_V3.models;

import jakarta.persistence.*;

@Entity
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue
    Long id;

    private String songTitle;
    private String artistName;
    private Boolean isFavorited;

    // Een collectie kan meerdere songs bevatten, een song kan in meerdere collecties zitten.
    @ManyToOne
    @JoinColumn(name = "song_collection_type_id")
    private SongCollectionType songCollectionType;

    public SongCollectionType getSongCollectionType() {
        return songCollectionType;
    }

    public void setSongCollectionType(SongCollectionType songCollectionType) {
        this.songCollectionType = songCollectionType;
    }

    public Song() {
    }

    public Song(Long id, String songTitle, String artistName, Boolean isFavorited) {
        this.id = id;
        this.songTitle = songTitle;
        this.artistName = artistName;
        this.isFavorited = isFavorited;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public Boolean getIsFavorited() {
        return isFavorited;
    }

    public void setIsFavorited(Boolean isFavorited) {
        this.isFavorited = isFavorited;
    }
}
