package Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.images;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

}
