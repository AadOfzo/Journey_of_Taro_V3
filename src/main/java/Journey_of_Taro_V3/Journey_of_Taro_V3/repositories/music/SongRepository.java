package Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long> {
//    List<Song> findAllSongsBySongTitleEqualsIgnoreCase(String songTitle);
//    List<Song> findAllSongsByArtistNameEqualsIgnoreCase(String artistName);
//    List<Song> findAllSongsBySongTitleAndArtistName(String songTitle, String artistName);

//    Optional<Song> findById(String songTitle);

}
