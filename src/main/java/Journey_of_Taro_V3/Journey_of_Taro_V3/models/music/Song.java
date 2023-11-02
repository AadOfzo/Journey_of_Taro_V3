package Journey_of_Taro_V3.Journey_of_Taro_V3.models.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.users.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue
    private Long id;

    private String songTitle;
    @ManyToOne
    @NotNull
    private User artistName;

    @ManyToOne
    @JoinColumn(name = "song_collection_type_id")
    @NotNull(message = "Please choose a Song Collection Type")
    private SongCollectionType songCollectionType;

    private Boolean isFavorited;
    private Long isFavoritedCounter;
    private Long playCounter;

    public void setArtistName(User artistName) {
        this.artistName = artistName;
    }

    public String getArtistName() {
        if (artistName != null) {
            return artistName.getArtistName();
        }
        return "Test Artist Name";
    }

    public Song() {
    }

    public Song(Long id, String songTitle, User artist, SongCollectionType songCollectionType, Boolean isFavorited, Long isFavoritedCounter, Long playCounter) {
        this.id = id;
        this.songTitle = songTitle;
        this.artistName = artist;
        this.songCollectionType = songCollectionType;
        this.isFavorited = isFavorited;
        this.isFavoritedCounter = isFavoritedCounter;
        this.playCounter = playCounter;
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
