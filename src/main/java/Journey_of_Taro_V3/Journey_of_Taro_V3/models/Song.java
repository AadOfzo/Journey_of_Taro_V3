package Journey_of_Taro_V3.Journey_of_Taro_V3.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue
    private Long id;

    private String songTitle;
    private String artistName;

    @ManyToOne
    @JoinColumn(name = "song_collection_type_id")
    @NotNull
    private SongCollectionType songCollectionType;

    private Boolean isFavorited;
    private Long isFavoritedCounter;
    private Long playCounter;

    // Getters and setters

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

    public SongCollectionType getSongCollectionType() {
        return songCollectionType;
    }

    public void setSongCollectionType(SongCollectionType songCollectionType) {
        this.songCollectionType = songCollectionType;
    }

    public Boolean getIsFavorited() {
        return isFavorited;
    }

    public void setIsFavorited(Boolean favorited) {
        isFavorited = favorited;
    }

    public Long getIsFavoritedCounter() {
        return isFavoritedCounter;
    }

    public void setIsFavoritedCounter(Long isFavoritedCounter) {
        this.isFavoritedCounter = isFavoritedCounter;
    }

    public Long getPlayCounter() {
        return playCounter;
    }

    public void setPlayCounter(Long playCounter) {
        this.playCounter = playCounter;
    }
}
