package Journey_of_Taro_V3.Journey_of_Taro_V3.models.music;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class SongCollectionType {

    @Id
    @GeneratedValue
    private Long id;
    // SongCollectionTypes
//    @Embedded
//    private Demo demo;
//
//    @Embedded
//    private Single single;
//
//    @Embedded
//    private EP ep;
//
//    @Embedded
//    private Album album;
//
//    @Embedded
//    private Meditation meditation;

    // SongCollection Image
    @Lob
    private byte[] image;

//    @OneToMany(mappedBy = "songCollectionType")
//    private List<Song> songs;

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
//
//    public Demo getDemo() {
//        return demo;
//    }
//
//    public void setDemo(Demo demo) {
//        this.demo = demo;
//    }
//
//    public Single getSingle() {
//        return single;
//    }
//
//    public void setSingle(Single single) {
//        this.single = single;
//    }
//
//    public EP getEp() {
//        return ep;
//    }
//
//    public void setEp(EP ep) {
//        this.ep = ep;
//    }
//
//    public Album getAlbum() {
//        return album;
//    }
//
//    public void setAlbum(Album album) {
//        this.album = album;
//    }
//
//    public Meditation getMeditation() {
//        return meditation;
//    }
//
//    public void setMeditation(Meditation meditation) {
//        this.meditation = meditation;
//    }

//    public List<Song> getSongs() {
//        return songs;
//    }

//    public void setSongs(List<Song> songs) {
//        this.songs = songs;
//    }

    public List<SongCollectionType> getSongCollectionTypes() {
        return songCollectionTypes;
    }

    public void setSongCollectionTypes(List<SongCollectionType> songCollectionTypes) {
        this.songCollectionTypes = songCollectionTypes;
    }
}
