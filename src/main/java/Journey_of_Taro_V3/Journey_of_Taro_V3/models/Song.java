package Journey_of_Taro_V3.Journey_of_Taro_V3.models;

import jakarta.persistence.*;

@Entity
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue
    Long id;
    private String songtitle;
    private String artistname;
    private Boolean isfavorited;

    public Song(Long id, String songtitle, String artistname, Boolean isfavorited) {
        this.id = id;
        this.songtitle = songtitle;
        this.artistname = artistname;
        this.isfavorited = isfavorited;
    }

    public Song() {

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
}
