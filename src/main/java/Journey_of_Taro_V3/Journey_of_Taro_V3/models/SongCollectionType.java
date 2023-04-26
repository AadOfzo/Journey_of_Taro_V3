package Journey_of_Taro_V3.Journey_of_Taro_V3.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import java.util.List;

@Entity
public class SongCollectionType {
    @EmbeddedId
    private SongCollectionTypeKey songCollectionId;

    private String type;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "music_collection_id")
    private MusicCollection musicCollection;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "song_id")
//    private Song song;
    @OneToMany(mappedBy = "song_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private List<Song> songs;
    @OneToMany(mappedBy = "song_collection_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private List<SongCollectionType> songCollectionTypes;

    public SongCollectionType() {
    }
    public SongCollectionType(SongCollectionTypeKey songCollectionId, String type, MusicCollection musicCollection, List<Song> songs, List<SongCollectionType> songCollectionTypes) {
        this.songCollectionId = songCollectionId;
        this.type = type;
        this.musicCollection = musicCollection;
        this.songs = songs;
        this.songCollectionTypes = songCollectionTypes;
    }

    public SongCollectionType(String songCollectionType) {
    }

    public SongCollectionTypeKey getSongCollectionId() {
        return songCollectionId;
    }

    public void setSongCollectionId(SongCollectionTypeKey songCollectionId) {
        this.songCollectionId = songCollectionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MusicCollection getMusicCollection() {
        return musicCollection;
    }

    public void setMusicCollection(MusicCollection musicCollection) {
        this.musicCollection = musicCollection;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public List<SongCollectionType> getSongCollectionTypes() {
        return songCollectionTypes;
    }

    public void setSongCollectionTypes(List<SongCollectionType> songCollectionTypes) {
        this.songCollectionTypes = songCollectionTypes;
    }

    public void addSong(Song song) {
        songs.add(song);
        song.setSongCollectionType(this);
    }

}