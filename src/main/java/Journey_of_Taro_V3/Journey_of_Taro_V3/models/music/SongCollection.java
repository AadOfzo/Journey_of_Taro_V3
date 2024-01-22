package Journey_of_Taro_V3.Journey_of_Taro_V3.models.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.Image;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name = "song_collections")
public class SongCollection {
    @Id
    @GeneratedValue
    private Long id;

//  Error 3860:  'SongCollection.songs' is 'mappedBy' another entity and may not specify the '@JoinColumn'
    @OneToMany(mappedBy = "songCollection", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "songs_in_collection", nullable = false)
    private List<Song> songs;
    @Enumerated(EnumType.STRING)
    private SongCollectionType songCollectionType;

    private String songCollectionTitle;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "song_collection_image", nullable = false)
    private Image image;


    public SongCollection() {
    }

    public SongCollection(SongCollectionType songCollectionType, String songCollectionTitle, Image image) {
        this.songs = new ArrayList<>();
        this.songCollectionType = songCollectionType;
        this.songCollectionTitle = songCollectionTitle;
        this.image = image;
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

    public void addSongs(Song song) {
        this.songs = songs;
    }

    public void showSongCollectionInformation(){
        System.out.println("There are " + songs.size() + "in Collection" + getSongCollectionType() + songCollectionTitle);
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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("In").append(songCollectionTitle).append("")
                .append(songCollectionType).append("there are")
                .append(songs.size()).append("songs");

        for (Song song : songs) {

            stringBuilder.append("\n\r").append(song.toString()).append(",");
        }
            stringBuilder.append("\n\r");
            return stringBuilder.toString();
        }
    }

