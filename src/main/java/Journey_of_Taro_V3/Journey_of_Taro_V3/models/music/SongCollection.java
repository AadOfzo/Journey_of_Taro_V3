package Journey_of_Taro_V3.Journey_of_Taro_V3.models.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.Image;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table( name = "song_collections")
public class SongCollection {

    @Id
    @GeneratedValue
    private Long id;

    private String songCollectionName;

    @OneToMany(mappedBy = "songCollection", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Song> songs;

    @OneToOne
    private Image collectionImage;

    private SongCollectionType songCollectionType;

    public SongCollection() {
    }

    public SongCollection(Long id, String songCollectionName, List<Song> songs, Image collectionImage, SongCollectionType songCollectionType) {
        this.id = id;
        this.songCollectionName = songCollectionName;
        this.songs = songs;
        this.collectionImage = collectionImage;
        this.songCollectionType = songCollectionType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSongCollectionName() {
        return songCollectionName;
    }

    public void setSongCollectionName(String songCollectionName) {
        this.songCollectionName = songCollectionName;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public Image getCollectionImage() {
        return collectionImage;
    }

    public void setCollectionImage(Image collectionImage) {
        this.collectionImage = collectionImage;
    }

    public SongCollectionType getSongCollectionType() {
        return songCollectionType;
    }

    public void setSongCollectionType(SongCollectionType songCollectionType) {
        this.songCollectionType = songCollectionType;
    }

    public SongCollection(Long id, String songCollectionName, List<Song> songs, Image collectionImage) {
        // ... other assignments
        this.songCollectionType = Song.categorizeSongs(songs.size());
    }
}
