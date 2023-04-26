package Journey_of_Taro_V3.Journey_of_Taro_V3.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SongCollectionTypeKey implements Serializable {

    @Column( name = "song_id")
    private Long songId;

    @Column( name = "song_collection_id")
    private Long songCollectionId;

//    @Column( name = "music_collection_id")
//    private Long musicCollectionId;

    public SongCollectionTypeKey() {
    }

    public SongCollectionTypeKey(Long songId, Long songCollectionId, Long musicCollectionId) {
        this.songId = songId;
        this.songCollectionId = songCollectionId;
//        this.musicCollectionId = musicCollectionId;
    }

    public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }

    public Long getSongCollectionId() {
        return songCollectionId;
    }

    public void setSongCollectionId(Long songCollectionId) {
        this.songCollectionId = songCollectionId;
    }
//
//    public Long getMusicCollectionId() {
//        return musicCollectionId;
//    }
//
//    public void setMusicCollectionId(Long musicCollectionId) {
//        this.musicCollectionId = musicCollectionId;
//    }
@Override
public boolean equals(Object o) {
    if(this == o) return true;
    if(o == null || getClass() != o.getClass()) return false;
    SongCollectionTypeKey that = (SongCollectionTypeKey) o;
    return songId.equals(that.songId)&& songCollectionId.equals(that.songCollectionId);
}
    @Override
    public int hashCode() {return Objects.hash(songId, songCollectionId);}
}
