package Journey_of_Taro_V3.Journey_of_Taro_V3.repositories;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.Authority;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.SongCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongCollectionRepository extends JpaRepository<SongCollection, Long> {
}

