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

    @ManyToMany(mappedBy = "songs")
    private Set<SongCollection> songCollections;

    @Enumerated(EnumType.STRING)
    @NotNull
    private SongCollectionType songCollectionType;

    public Song() {
    }

    public Song(Long id, String songTitle, Set<SongCollection> songCollections, SongCollectionType songCollectionType) {
        this.id = id;
        this.songTitle = songTitle;
        this.songCollections = songCollections;
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

    public Set<SongCollection> getSongCollections() {
        return songCollections;
    }

    public void setSongCollections(Set<SongCollection> songCollections) {
        this.songCollections = songCollections;
    }

    public SongCollectionType getSongCollectionType() {
        return songCollectionType;
    }

    public void setSongCollectionType(SongCollectionType songCollectionType) {
        this.songCollectionType = songCollectionType;
    }

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
