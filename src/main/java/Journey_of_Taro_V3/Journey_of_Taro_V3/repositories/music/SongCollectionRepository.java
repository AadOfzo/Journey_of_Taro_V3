package Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.SongCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongCollectionRepository extends JpaRepository<SongCollection,Long> {
}
