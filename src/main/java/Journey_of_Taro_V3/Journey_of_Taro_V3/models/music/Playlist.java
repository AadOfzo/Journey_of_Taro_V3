package Journey_of_Taro_V3.Journey_of_Taro_V3.models.music;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Playlist {

    @Id
    @GeneratedValue
    private Long id;

    private String playlistName;

    @OneToMany
    private List<Song> songs;

    private boolean isPublic;

    public Playlist() {
        // Default constructor for JPA
    }

    public Playlist(List<Song> songs) {
        this.songs = songs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }
}
