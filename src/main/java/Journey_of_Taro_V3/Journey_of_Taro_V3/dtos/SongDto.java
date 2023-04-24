package Journey_of_Taro_V3.Journey_of_Taro_V3.dtos;

public class SongDto {
    private Long id;
    public String songtitle;
    public String artistname;
    public Boolean isfavorited;

    public SongDto() {
    }

    public SongDto(Long id, String songtitle, String artistname, Boolean isfavorited) {
        this.id = id;
        this.songtitle = songtitle;
        this.artistname = artistname;
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
