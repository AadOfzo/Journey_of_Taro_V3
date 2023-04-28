package Journey_of_Taro_V3.Journey_of_Taro_V3.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "song_collection_types")
public class SongCollectionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "song_collection_type")
    private String songCollectionType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSongCollectionType() {
        return songCollectionType;
    }

    public void setSongCollectionType(String songCollectionType) {
        this.songCollectionType = songCollectionType;
    }
}
