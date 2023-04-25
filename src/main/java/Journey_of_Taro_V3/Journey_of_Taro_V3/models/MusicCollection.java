package Journey_of_Taro_V3.Journey_of_Taro_V3.models;

import java.util.ArrayList;
import java.util.List;

public class MusicCollection {
    private List<SongCollectionType> songCollectionTypes;

    public MusicCollection() {
        songCollectionTypes = new ArrayList<>();
    }

    public void addSongCollectionType(SongCollectionType songCollectionType) {
        songCollectionTypes.add(songCollectionType);
    }

    public List<SongCollectionType> getSongCollectionTypes() {
        return songCollectionTypes;
    }

    public void addSongToSongCollectionType(Song song, int songCollectionTypeIndex) {
        if (songCollectionTypeIndex >= 0 && songCollectionTypeIndex < songCollectionTypes.size()) {
            SongCollectionType songCollectionType = songCollectionTypes.get(songCollectionTypeIndex);
            songCollectionType.addSong(song);
        } else {
            throw new IndexOutOfBoundsException("Invalid song collection type index");
        }
    }
}
