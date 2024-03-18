package Journey_of_Taro_V3.Journey_of_Taro_V3.models.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.CustomMultipartFile;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.Image;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @OneToMany(mappedBy = "songCollection")
    private List<Song> songs;

    public SongCollection() {
        songs = new ArrayList<>();
    }

    public void addSong(Song song) {
        songs.add(song);
        song.setSongCollection(this);
    }

    public List<Song> getSongs() {
        return songs;
    }

    public String getSongCollectionTitle() {
        return songCollectionTitle;
    }

    public void setSongCollectionTitle(String songCollectionTitle) {
        this.songCollectionTitle = songCollectionTitle;
    }


    //    @ManyToMany
//    @JoinTable(
//            name = "song_collection_songs",
//            joinColumns = @JoinColumn(name = "song_collection_id"),
//            inverseJoinColumns = @JoinColumn(name = "song_id"));

//    @ManyToOne
//    @JoinColumn(name = "song_collection_songs", referencedColumnName = "song_id")
//    private List<Long> songIds;

//    public SongCollection() {
//
//    }
//
//    public SongCollection(List<Long> songIds) {
//        this.songIds = songIds;
//    }
//

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Song Collection: ").append(id).append("\n")
                .append("Number of songs: ").append(songs.size()).append("\n")
                .append("Songs:\n");
        for (Song songId : songs) {
            stringBuilder.append(songId).append("\n");
        }
        return stringBuilder.toString();
    }


    public void addSongsToCollection(List<Song> songsToAdd) {

        songs.addAll(songsToAdd);
    }
}

