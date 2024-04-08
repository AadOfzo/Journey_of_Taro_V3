package Journey_of_Taro_V3.Journey_of_Taro_V3.services.users;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.users.UserDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.RecordNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.Song;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.SongCollection;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.security.Authority;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.users.Role;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.users.User;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.images.ImageRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music.SongRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.users.RoleRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.users.UserRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.utils.RandomStringGenerator;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ImageRepository imageRepository;
    private final SongRepository songRepository;
    private final PasswordEncoder passwordEncoder;

    // TODO: 29/02/2024 Could not autowire PasswordEncoder code werkt wel. Users kunnen aangemaakt worden.
    // https://www.baeldung.com/spring-security-registration-password-encoding-bcrypt


    public UserService(UserRepository userRepository, RoleRepository roleRepository, ImageRepository imageRepository, SongRepository songRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.imageRepository = imageRepository;
        this.songRepository = songRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> getUserByArtistName(String artistName) {
        return userRepository.findByArtistName(artistName);
    }

    public UserDto getUserByApiKey(String apikey) {
        User user = userRepository.findByApikey(apikey);
        if (user != null) {
            return fromUser(user);
        } else {
            throw new UsernameNotFoundException("User not found with apikey: " + apikey);
        }
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


    public User saveUser(UserDto userDto) {
        User user = toUser(userDto); // Convert UserDto to User
        return userRepository.save(user);
    }

    public String createUser(UserDto userDto) {
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        userDto.setApikey(randomString);
        User newUser = userRepository.save(toUser(userDto));
        return newUser.getUsername();
    }

    // todo Connect User artistName & Song artistName
//    public void updateArtistNameIfMatch(UserDto userDto, SongDto songDto) {
//        // Get the artist name from the UserDto object
//        String userArtistName = userDto.getArtistName();
//
//        // Get the artist name from the SongDto object
//        String songArtistName = songDto.getArtistName();
//
//        // Check if the artist names match
//        if (userArtistName.equals(songArtistName)) {
//            // If they match, update the artist name for both UserDto and SongDto objects
//            userDto.setArtistName(songArtistName);
//            songDto.setArtistName(songArtistName);
//        }
//    }

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
        if (!userRepository.existsByUsername(username)) {
            throw new UsernameNotFoundException(username);
        }
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return user.getRoles();
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

    @Transactional
    public void grantAdminPrivilege(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Retrieve the admin role from the database or create a new one if it doesn't exist
        Role adminRole = roleRepository.findByRoleName(Role.ROLE_ADMIN)
                .orElseGet(() -> {
                    Role newAdminRole = new Role();
                    newAdminRole.setRoleName(Role.ROLE_ADMIN);
                    return roleRepository.save(newAdminRole);
                });

        // Add the admin role to the user's roles
        user.addRole(adminRole);

        // Save the updated user
        userRepository.save(user);
    }


}
