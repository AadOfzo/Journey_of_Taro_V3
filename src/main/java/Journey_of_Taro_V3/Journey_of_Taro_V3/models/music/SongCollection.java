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

    @OneToMany(mappedBy = "songCollection", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Song> songs;
    @Enumerated(EnumType.STRING)
    private SongCollectionType songCollectionType;

    private String songCollectionTitle;
    @OneToOne(cascade = CascadeType.ALL)
    private Image image;


    public SongCollection() {
    }

    public SongCollection(List<Song> songs, String songCollectionTitle, Image image) {
        this.songs = songs;
        this.songCollectionTitle = songCollectionTitle;
        this.image = image;
        this.songCollectionType = determineSongCollectionType(songs.size());
    }

    private SongCollectionType determineSongCollectionType(int numberOfSongs) {
        if (numberOfSongs == 1) {
            return SongCollectionType.Demos;
        } else if (numberOfSongs >= 2 && numberOfSongs <= 6) {
            return SongCollectionType.EPs;
        } else {
            return SongCollectionType.Albums;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public SongCollectionType getSongCollectionType() {
        return songCollectionType;
    }

    public void setSongCollectionType(SongCollectionType songCollectionType) {
        this.songCollectionType = songCollectionType;
        // Logica voor SongCollectionType
    }

    public String getSongCollectionTitle() {
        return songCollectionTitle;
    }

    public void setSongCollectionTitle(String songCollectionTitle) {
        this.songCollectionTitle = songCollectionTitle;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
