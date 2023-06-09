package Journey_of_Taro_V3.Journey_of_Taro_V3.repositories;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SongRepository extends JpaRepository<Song, Long> {
    Optional<Song> findBySongtitleIgnoreCase(String songtitle);
//        List<Song> findAllSongsByIdEqualsIgnoreCase(Long Id);

        List<Song> findAllSongsBySongtitleEqualsIgnoreCase(String songtitle);

//        Optional<Song> findById(String songtitle);
}
