package Journey_of_Taro_V3.Journey_of_Taro_V3.services.users;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.users.UserDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.RecordNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.security.Authority;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.users.Role;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.users.User;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.users.UserRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.utils.RandomStringGenerator;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // TODO: 29/02/2024 Could not autowire PasswordEncoder code werkt wel. Users kunnen aangemaakt worden.
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> getUserByArtistName(String artistName) {
        return userRepository.findByArtistName(artistName);
    }

    public List<UserDto> getUsers() {
        List<UserDto> collection = new ArrayList<>();
        List<User> list = userRepository.findAll();
        for (User user : list) {
            collection.add(fromUser(user));
        }
        return collection;
    }

    public UserDto getUser(String username) {
        UserDto dto = new UserDto();
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()) {
            dto = fromUser(user.get());
        } else {
            throw new UsernameNotFoundException(username);
        }
        return dto;
    }

    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public String createUser(UserDto userDto) {
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        userDto.setApikey(randomString);
        User newUser = userRepository.save(toUser(userDto));
        return newUser.getUsername();
    }

    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }

    public void updateUser(String username, UserDto newUser) {
        if (!userRepository.existsByUsername(username)) throw new RecordNotFoundException();
        User user = userRepository.findById(username).orElseThrow(() -> new RecordNotFoundException("User not found with username: " + username));
        user.setPassword(passwordEncoder.encode(newUser.getPassword())); // Encode the password before setting
        userRepository.save(user);
    }

    public List<Role> getRoles(String username) {
        if (!userRepository.existsByUsername(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        UserDto userDto = fromUser(user);
        return userDto.getRoles();
    }

    public void addRole(String username, String roleName) {
        if (!userRepository.existsByUsername(username)) {
            throw new UsernameNotFoundException(username);
        }

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        Role role = new Role();
        role.setRoleName(roleName);
        user.addRole(role);
        userRepository.save(user);
    }

    public static UserDto fromUser(User user) {

        var dto = new UserDto();

        dto.username = user.getUsername();
        dto.password = user.getPassword();
        dto.enabled = user.isEnabled();
        dto.apikey = user.getApikey();
        dto.email = user.getEmail();
        dto.artistName = user.getArtistName();
//        dto.roles = user.getAuthorities();

        return dto;
    }

    public User toUser(UserDto userDto) {

        var user = new User();

        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.password));
        user.setEnabled(userDto.getEnabled());
        user.setApikey(userDto.getApikey());
        user.setEmail(userDto.getEmail());
        user.setArtistName(userDto.getArtistName());

        return user;
    }

}
