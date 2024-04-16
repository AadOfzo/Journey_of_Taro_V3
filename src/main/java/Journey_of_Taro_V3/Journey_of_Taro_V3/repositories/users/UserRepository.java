package Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.users;


import Journey_of_Taro_V3.Journey_of_Taro_V3.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    Optional<User> findByArtistName(String artistName);
    Boolean existsByUsername (String username);

    User findByApikey(String apikey);

}
