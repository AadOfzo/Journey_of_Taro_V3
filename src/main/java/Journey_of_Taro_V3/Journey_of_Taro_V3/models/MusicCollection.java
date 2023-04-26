package Journey_of_Taro_V3.Journey_of_Taro_V3.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "musicCollection")
public class MusicCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String musicCollectionName;
    @OneToMany(mappedBy = "musicCollection", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SongCollectionType> songCollectionTypes = new ArrayList<>();

    public void addSongCollectionType(SongCollectionType songCollectionType) {
        songCollectionTypes.add(songCollectionType);
        songCollectionType.setMusicCollection(this);
    }

    public void removeSongCollectionType(SongCollectionType songCollectionType) {
        songCollectionTypes.remove(songCollectionType);
        songCollectionType.setMusicCollection(null);
    }
    public MusicCollection() {
        songCollectionTypes = new ArrayList<>();
    }
    // Getters and setters for id and songCollectionTypes omitted for brevity
    public String getMusicCollectionName() {
        return musicCollectionName;
    }

    public void setMusicCollectionName(String musicCollectionName) {
        this.musicCollectionName = musicCollectionName;
    }


    public List<SongCollectionType> getSongCollectionTypes() {
        return songCollectionTypes;
    }

    public void addSongToSongCollectionType(Song song, int songCollectionTypeIndex) {
        if (songCollectionTypeIndex >= 0 && songCollectionTypeIndex < songCollectionTypes.size()) {
            SongCollectionType songCollectionType = songCollectionTypes.get(songCollectionTypeIndex);
            songCollectionType.addSong(song);
        } else {
            throw new IndexOutOfBoundsException("Invalid song collection type index");
        }
    }

    public void setSongCollectionTypes(List<SongCollectionType> songCollectionTypes) {
        this.songCollectionTypes = songCollectionTypes;
    }
}
