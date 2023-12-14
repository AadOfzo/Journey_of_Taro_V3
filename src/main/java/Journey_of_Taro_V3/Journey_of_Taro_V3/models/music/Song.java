package Journey_of_Taro_V3.Journey_of_Taro_V3.models.music;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue
    private Long id;

    private String songTitle;

    @ManyToOne
    @JoinColumn(name = "song_collection_id")
    private SongCollection songCollection;

    @Enumerated(EnumType.STRING)
    @NotNull
    private SongCollectionType songCollectionType;

    public Song() {
    }

    public Song(Long id, String songTitle, SongCollection songCollection, SongCollectionType songCollectionType) {
        this.id = id;
        this.songTitle = songTitle;
        this.songCollection = songCollection;
        this.songCollectionType = songCollectionType;
    }

    public Song(Long id, String songTitle, SongCollectionType songCollectionType) {
        this.id = id;
        this.songTitle = songTitle;
        this.songCollectionType = songCollectionType;
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

    public SongCollection getSongCollections() {
        return songCollection;
    }

    public void setSongCollections(SongCollection songCollections) {
        this.songCollection = songCollections;
    }

    public SongCollectionType getSongCollectionType() {
        return songCollectionType;
    }

    public void setSongCollectionType(SongCollectionType songCollectionType) {
        this.songCollectionType = songCollectionType;
    }
//    Deze if statement klopt nog niet! Lijst returnen voor opties met 1 Song, EP en Album.
    public static SongCollectionType categorizeSongs(int numberOfSongs) {
        if (numberOfSongs == 1) {
            return SongCollectionType.Demo;
        } else if (numberOfSongs >= 2 && numberOfSongs <= 6) {
            return SongCollectionType.EP;
        } else {
            return SongCollectionType.Album;
        }
    }
}
