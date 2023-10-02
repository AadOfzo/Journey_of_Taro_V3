package Journey_of_Taro_V3.Journey_of_Taro_V3.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class SongCollectionType {

    @Id
    @GeneratedValue
    private Long id;
    private String demo;
    private String sampledemo;
    private String single;
    private String ep;
    private String album;
    private String meditations;

    @OneToMany(mappedBy = "songCollectionType")
    private List<Song> songs;

    @OneToMany(mappedBy = "songCollectionType")
    @JsonIgnore
    List<SongCollectionType> songCollectionTypes;

    @ManyToOne
    @JoinColumn(name = "song_collection_type_id")
    private SongCollectionType songCollectionType;

    public SongCollectionType getSongCollectionType() {
        return songCollectionType;
    }

    public void setSongCollectionType(SongCollectionType songCollectionType) {
        this.songCollectionType = songCollectionType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDemo() {
        return demo;
    }

    public void setDemo(String demo) {
        this.demo = demo;
    }

    public String getSampledemo() {
        return sampledemo;
    }

    public void setSampledemo(String sampledemo) {
        this.sampledemo = sampledemo;
    }

    public String getSingle() {
        return single;
    }

    public void setSingle(String single) {
        this.single = single;
    }

    public String getEp() {
        return ep;
    }

    public void setEp(String ep) {
        this.ep = ep;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getMeditations() {
        return meditations;
    }

    public void setMeditations(String meditations) {
        this.meditations = meditations;
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
}
