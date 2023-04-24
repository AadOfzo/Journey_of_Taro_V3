package Journey_of_Taro_V3.Journey_of_Taro_V3.repositories;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.Song;

import java.util.List;
import java.util.Optional;

public interface SongCollectionTypeRepository extends SongRepository {
    List<Song> findAllSongsBySongtitleEqualsIgnoreCase(String songtitle);

    Optional<Song> findById(String songtitle);

//    List<SongCollectionType> findAllSongCollectionsBySongCollectionTitleEqualsIgnoreCase(String songcollectiontitle);
}
