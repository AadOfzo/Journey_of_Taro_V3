package Journey_of_Taro_V3.Journey_of_Taro_V3.models;

import jakarta.persistence.*;

@Entity
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue
    Long id;

    private String songtitle;
    private Boolean isfavorited;

    // Een collectie kan meerdere songs bevatten, een song kan in meerdere collecties zitten.
    @ManyToOne
    @JoinColumn(name = "song_collection_type_id")
    private SongCollectionType songCollectionType;

    public SongCollectionType getSongCollectionType() {
        return songCollectionType;
    }

    public void setSongCollectionType(SongCollectionType songCollectionType) {
        this.songCollectionType = songCollectionType;
    }

    public Song() {
    }

    public Song(Long id, String songtitle, Boolean isfavorited) {
        this.id = id;
        this.songtitle = songtitle;
        this.isfavorited = isfavorited;
    }

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

    public Boolean getIsfavorited() {
        return isfavorited;
    }

    public void setIsfavorited(Boolean isfavorited) {
        this.isfavorited = isfavorited;
    }
}
