package Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.users;


import Journey_of_Taro_V3.Journey_of_Taro_V3.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
