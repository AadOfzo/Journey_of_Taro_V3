package Journey_of_Taro_V3.Journey_of_Taro_V3.models.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.users.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

@Entity
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue
    private Long id;

    private String songTitle;

    @ManyToMany(mappedBy = "songs")
    private Set<SongCollection> songCollections;

    public Song() {
    }

    public Song(Long id, String songTitle, Set<SongCollection> songCollections) {
        this.id = id;
        this.songTitle = songTitle;
        this.songCollections = songCollections;
    }

    public Song(Long id, String songTitle) {
        this.id = id;
        this.songTitle = songTitle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public Set<SongCollection> getSongCollections() {
        return songCollections;
    }

    public void setSongCollections(Set<SongCollection> songCollections) {
        this.songCollections = songCollections;
    }
}

