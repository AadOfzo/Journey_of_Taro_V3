package Journey_of_Taro_V3.Journey_of_Taro_V3.models;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.Song;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.SongCollectionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "song_collections")
public class SongCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @ManyToOne
    @JoinColumn(name = "song_collection_type_id")
    private SongCollectionType songCollectionType;
    @ManyToOne
    @JoinColumn(name = "song_id")
    private Song song;

    public SongCollection() {
    }

    public SongCollection(Long id, String name, Song song, SongCollectionType songCollectionType) {
        this.id = id;
        this.name = name;
        this.song = song;
        this.songCollectionType = songCollectionType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public SongCollectionType getSongCollectionType() {
        return songCollectionType;
    }

    public void setSongCollectionType(SongCollectionType songCollectionType) {
        this.songCollectionType = songCollectionType;
    }
}
