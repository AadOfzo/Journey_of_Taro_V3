package Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.televisions;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.televisions.Television;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TelevisionRepository extends JpaRepository<Television, Long> {
    List<Television> findAllTelevisionsByBrandEqualsIgnoreCase(String brand);
}
