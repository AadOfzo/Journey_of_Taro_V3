package Journey_of_Taro_V3.Journey_of_Taro_V3.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Entity
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue
    Long id;
    private String songtitle;
    private String artistname;
    private Boolean isfavorited;

    private static List<Song> songs = new ArrayList<>();

    static {
        songs.add(new Song(1L, "Song Title 1", "Artist 1", false));
        songs.add(new Song(2L, "Song Title 2", "Artist 2", true));
        // add more songs if needed
    }

    public Song() {
    }

    public Song(Long id, String songtitle, String artistname, Boolean isfavorited) {
        this.id = id;
        this.songtitle = songtitle;
        this.artistname = artistname;
        this.isfavorited = isfavorited;
    }

    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSongtitle() {
        return songtitle;
    }

    public void setSongtitle(String songtitle) {
        this.songtitle = songtitle;
    }

    public String getArtistname() {
        return artistname;
    }

    public void setArtistname(String artistname) {
        this.artistname = artistname;
    }

    public Boolean getIsfavorited() {
        return isfavorited;
    }

    public void setIsfavorited(Boolean isfavorited) {
        this.isfavorited = isfavorited;
    }

    public static List<Song> getSongs() {
        return songs;
    }

    public static void setSongs(List<Song> songs) {
        Song.songs = songs;
    }

    public static Stream<Song> stream() {
        return songs.stream();
    }
}

