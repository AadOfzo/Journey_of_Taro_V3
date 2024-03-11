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

    @ManyToMany
    @JoinTable(
            name = "song_collection_song",
            joinColumns = @JoinColumn(name = "song_collection_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id"))
    private List<Song> songs = new ArrayList<>();

    public void addSong(Song song) {
        songs.add(song);
        song.getSongCollections().add(this); // Bidirectional association
    }

    public void addSongs(List<Song> songs) {
        this.songs.addAll(songs);
        for (Song song : songs) {
            song.getSongCollections().add(this); // Bidirectional association
        }
    }


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
//    public Long getId() {
//        return id;
//    }
//
//    public List<Long> getSongIds() {
//        return songIds;
//    }
//
//    @Override
//    public String toString() {
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("Song Collection: ").append(id).append("\n")
//                .append("Number of songs: ").append(songIds.size()).append("\n")
//                .append("Songs:\n");
//        for (Long songId : songIds) {
//            stringBuilder.append(songId).append("\n");
//        }
//        return stringBuilder.toString();
//    }
}

