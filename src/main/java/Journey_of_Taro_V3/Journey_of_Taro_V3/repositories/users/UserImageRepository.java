package Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.users;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserImageRepository extends JpaRepository<UserImage, String> {
    Optional<UserImage> findUserImageByImageName(String imageName);
    List<UserImage> findAllByImageName(String imageName);

}
