package Journey_of_Taro_V3.Journey_of_Taro_V3.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

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

    public SongCollection() {
    }

    public SongCollection(Long id, String name, SongCollectionType songCollectionType) {
        this.id = id;
        this.name = name;
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

    public SongCollectionType getSongCollectionType() {
        return songCollectionType;
    }

    public void setSongCollectionType(SongCollectionType songCollectionType) {
        this.songCollectionType = songCollectionType;
    }
}
