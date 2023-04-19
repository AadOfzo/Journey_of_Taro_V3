package Journey_of_Taro_V3.Journey_of_Taro_V3.repositories;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.TelevisionWallBracket;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.TelevisionWallBracketKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface TelevisionWallBracketRepository extends JpaRepository<TelevisionWallBracket, TelevisionWallBracketKey> {
    List<TelevisionWallBracket> findAllByTelevisionId(Long televisionId);
    List<TelevisionWallBracket> findAllByWallBracketId(Long wallBracketId);
}
