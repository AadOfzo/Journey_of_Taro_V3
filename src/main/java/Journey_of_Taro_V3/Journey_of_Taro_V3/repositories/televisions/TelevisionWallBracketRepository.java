package Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.televisions;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.televisions.TelevisionWallBracket;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.televisions.TelevisionWallBracketKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TelevisionWallBracketRepository extends JpaRepository<TelevisionWallBracket, TelevisionWallBracketKey> {
    List<TelevisionWallBracket> findAllByTelevisionId(Long televisionId);
    List<TelevisionWallBracket> findAllByWallBracketId(Long wallBracketId);
}
