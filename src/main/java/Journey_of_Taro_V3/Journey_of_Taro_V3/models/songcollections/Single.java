package Journey_of_Taro_V3.Journey_of_Taro_V3.models.songcollections;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.Song;

import java.util.List;

public class Single {
    private List<Song> songs;

    public Single(List<Song> songs) {
        this.songs = songs;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}
