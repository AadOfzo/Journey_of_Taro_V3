package Journey_of_Taro_V3.Journey_of_Taro_V3.models.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.Image;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "song_collections")
public class SongCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String songCollectionTitle;

    @OneToMany(mappedBy = "songCollection", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Song> songs;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Image collectionImage;

    private String songCollectionUrl;

    public SongCollection() {
        songs = new ArrayList<>();
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

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public Image getCollectionImage() {
        return collectionImage;
    }

    public String getSongCollectionUrl() {
        return songCollectionUrl;
    }

    public void setSongCollectionUrl(String songCollectionUrl) {
        this.songCollectionUrl = songCollectionUrl;
    }

    public void setCollectionImage(Image collectionImage) {
        this.collectionImage = collectionImage;
    }
}
    
