package Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.users;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.UserSong;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSongRepository extends JpaRepository<UserSong, String> {
    Optional<UserSong> findUserSongBySongTitle(String songTitle);
}
