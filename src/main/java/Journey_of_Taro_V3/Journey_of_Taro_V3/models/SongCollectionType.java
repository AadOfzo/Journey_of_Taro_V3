package Journey_of_Taro_V3.Journey_of_Taro_V3.models;

import java.util.ArrayList;
import java.util.List;

public class SongCollectionType {
    private String type;
    private List<Song> songs;

    public SongCollectionType(String type) {
        this.type = type;
        songs = new ArrayList<>();
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public List<Song> getSongs() {
        return songs;
    }

    public String getType() {
        return type;
    }
}