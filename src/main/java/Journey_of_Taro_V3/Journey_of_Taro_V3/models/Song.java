package Journey_of_Taro_V3.Journey_of_Taro_V3.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue
    Long id;
    private String songtitle;
    private String artistname;
    private Boolean isfavorited;
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "song_id", referencedColumnName = "SONG_ID"),
            @JoinColumn(name = "song_collection_id", referencedColumnName = "SONG_COLLECTION_ID"),
            @JoinColumn(name = "music_collection_id", referencedColumnName = "MUSIC_COLLECTION_ID")
    })
    private SongCollectionType songCollectionType;

    public Song() {
    }

    public Song(Long id, String songtitle, String artistname, Boolean isfavorited, SongCollectionType songCollectionType) {
        this.id = id;
        this.songtitle = songtitle;
        this.artistname = artistname;
        this.isfavorited = isfavorited;
        this.songCollectionType = songCollectionType;
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

    public SongCollectionType getSongCollectionType() {
        return songCollectionType;
    }

    public void setSongCollectionType(SongCollectionType songCollectionType) {
        this.songCollectionType = songCollectionType;
    }
}
