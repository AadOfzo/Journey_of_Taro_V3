package Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.songcollectiontypes;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.Song;

import java.util.List;

public class Demo {
    private List<Song> songs;

    public Demo(List<Song> songs) {
        this.songs = songs;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}
