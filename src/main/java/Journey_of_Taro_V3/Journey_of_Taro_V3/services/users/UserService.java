package Journey_of_Taro_V3.Journey_of_Taro_V3.services.users;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.users.UserDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.RecordNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.Image;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.Song;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.security.Authority;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.users.User;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.UserImage;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.images.ImageRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.images.UserImageRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music.SongRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.users.UserRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.images.ImageServiceImpl;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.music.SongServiceImpl;
import Journey_of_Taro_V3.Journey_of_Taro_V3.utils.RandomStringGenerator;
import jakarta.transaction.Transactional;
import org.springframework.core.io.Resource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserImageRepository userImageRepository;
    private final ImageRepository imageRepository;
    private final ImageServiceImpl imageService;
    private final SongRepository songRepository;
    private final SongServiceImpl songService;
    private final PasswordEncoder passwordEncoder;


    // Could not autowire PasswordEncoder passwords komen wel encoded in database.
    // https://www.baeldung.com/spring-security-registration-password-encoding-bcrypt
    public UserService(UserRepository userRepository, ImageRepository imageRepository, UserImageRepository userImageRepository, ImageServiceImpl imageService, SongRepository songRepository, SongServiceImpl songService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userImageRepository = userImageRepository;
        this.imageRepository = imageRepository;
        this.imageService = imageService;
        this.songRepository = songRepository;
        this.songService = songService;
        this.passwordEncoder = passwordEncoder;
    }

    public String createUser(UserDto userDto) {
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        userDto.setApikey(randomString);
        User newUser = userRepository.save(toUser(userDto));
        return newUser.getUsername();
    }

    public List<UserDto> getUsers() {
        List<UserDto> collection = new ArrayList<>();
        List<User> list = userRepository.findAll();
        for (User user : list) {
            collection.add(fromUser(user));
        }
        return collection;
    }

    public UserDto getUserByUserName(String username) {
        UserDto dto;
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()) {
            dto = fromUser(user.get());
        } else {
            throw new UsernameNotFoundException(username);
        }
        return dto;
    }

    public UserDto getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findByUserId(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return fromUser(user);
        } else {
            throw new UsernameNotFoundException("User with ID: " + userId + " not found!");
        }
    }

    public UserDto getUserByApiKey(String apikey) {
        User user = userRepository.findByApikey(apikey);
        if (user != null) {
            return fromUser(user);
        } else {
            throw new UsernameNotFoundException("User not found with apikey: " + apikey);
        }
    }

    public UserDto convertUserToDto(User user) {
        UserDto userDto = new UserDto();

        userDto.setUserId(user.getUserId());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setApikey(user.getApikey());
        userDto.setFirstname(user.getFirstName());
        userDto.setLastname(user.getLastName());
        userDto.setDateofbirth(user.getDateOfBirth());
        userDto.setEmail(user.getEmail());
        userDto.setCountry(user.getCountry());
        userDto.setUserimage(user.getUserImage());
        userDto.setUserSong(user.getUserSong());
        userDto.setArtistname(user.getArtistName());

        // Baseer roles in authorities
        List<String> roles = user.getAuthorities().stream()
                .map(authority -> authority.getAuthority().substring(5)) // Remove "ROLE_" prefix
                .collect(Collectors.toList());
        userDto.setRoles(roles);

        return userDto;
    }


    public static UserDto fromUser(User user) {

        var dto = new UserDto();

        dto.userId = user.getUserId();
        dto.username = user.getUsername();
        dto.password = user.getPassword();
        dto.apikey = user.getApikey();
        dto.firstname = user.getFirstName();
        dto.lastname = user.getLastName();
        dto.dateofbirth = user.getDateOfBirth();
        dto.email = user.getEmail();
        dto.country = user.getCountry();
        dto.userimage = user.getUserImage();
        dto.userSong = user.getUserSong();
        dto.artistname = user.getArtistName();

        ArrayList authorities = new ArrayList<>();
        for (Authority authority : user.getAuthorities()) {
            authorities.add(authority.getAuthority());

        }
        dto.roles = authorities;
        return dto;
    }

    public User toUser(UserDto userDto) {

        var user = new User();

        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.password));
        user.setApikey(userDto.getApikey());
        user.setFirstName(userDto.getFirstname());
        user.setLastName(userDto.getLastname());
        user.setCountry(userDto.getCountry());
        user.setDateOfBirth(userDto.getDateofbirth());
        user.setEmail(userDto.getEmail());
        user.setArtistName(userDto.getArtistname());

        user.setUserImage(userDto.getUserimage());
        user.setUserSong(userDto.getUserSong());

        return user;
    }

    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }

    public UserDto updateUser(Long userId, UserDto newUserDto) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RecordNotFoundException("User not found with ID: " + userId));

        // Update user fields based on newUserDto
        if (newUserDto.getPassword() != null && !newUserDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(newUserDto.getPassword()));
        }
        if (newUserDto.getUsername() != null && !newUserDto.getUsername().isEmpty()) {
            user.setUsername(newUserDto.getUsername());
        }
        if (newUserDto.getFirstname() != null && !newUserDto.getFirstname().isEmpty()) {
            user.setFirstName(newUserDto.getFirstname());
        }
        if (newUserDto.getLastname() != null && !newUserDto.getLastname().isEmpty()) {
            user.setLastName(newUserDto.getLastname());
        }
        if (newUserDto.getDateofbirth() != null) {
            user.setDateOfBirth(newUserDto.getDateofbirth());
        }
        if (newUserDto.getCountry() != null && !newUserDto.getCountry().isEmpty()) {
            user.setCountry(newUserDto.getCountry());
        }
        if (newUserDto.getEmail() != null && !newUserDto.getEmail().isEmpty()) {
            user.setEmail(newUserDto.getEmail());
        }
        if (newUserDto.getUserimage() != null) {
            user.setUserImage(newUserDto.getUserimage());
        }
        if (newUserDto.getUserSong() != null) {
            user.setUserSong(newUserDto.getUserSong());
        }
        if (newUserDto.getArtistname() != null && !newUserDto.getArtistname().isEmpty()) {
            user.setArtistName(newUserDto.getArtistname());
        }
        if (newUserDto.getRoles() != null && !newUserDto.getRoles().isEmpty()) {
            Set<Authority> authorities = new HashSet<>();
            for (String role : newUserDto.getRoles()) {
                authorities.add(new Authority(user, role));
            }
            user.setAuthorities(authorities);
        }

        // Save the updated user entity
        User updatedUser = userRepository.save(user);

        // Convert updatedUser to UserDto
        return convertUserToDto(updatedUser);
    }

    // Roles & Authorities methods relation User --> roles / authorities.
    public Set<Authority> getRoles(String username) {
        if (!userRepository.existsByUsername(username)) {
            throw new UsernameNotFoundException(username);
        }
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return user.getAuthorities();
    }

    public void addRole(String username, String roleName) {
        if (!userRepository.existsByUsername(username)) {
            throw new UsernameNotFoundException(username);
        }
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        Authority authority = new Authority();
        authority.setAuthority(roleName);
        user.addAuthorities(authority);
        userRepository.save(user);
    }

    @Transactional
    public void grantAdminPrivilege(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        Authority adminAuthority = new Authority(user, "ROLE_ADMIN");

        // Add the admin role to the user's roles
        user.addAuthorities(adminAuthority);

        // Save the updated user
        userRepository.save(user);
    }

    // UserImage methods relation User --> Image
    @Transactional
    public User assignImageToUser(Long userId, String imageName) {
        // Fetch the user by ID
        Optional<User> optionalUser = userRepository.findByUserId(userId);
        Optional<UserImage> optionalUserImage = userImageRepository.findUserImageByImageName(imageName);
        // Als user en image bestaan, assign image aan user
        if (optionalUser.isPresent() && optionalUserImage.isPresent()) {
            User user = optionalUser.get();
            UserImage userImage = optionalUserImage.get();
            user.setUserImage(userImage);
            return userRepository.save(user);
        } else {
            throw new RecordNotFoundException();
        }
    }

    @Transactional
    public Resource getImageFromUser(Long userId) {
        Optional<User> optionalUser = userRepository.findByUserId(userId);
        if (optionalUser.isEmpty()) {
            throw new RecordNotFoundException("User " + userId + " not found. ");
        }
        UserImage userImage = optionalUser.get().getUserImage();
        if (userImage == null) {
            throw new RecordNotFoundException("User " + userId + " has no Image");
        }
        return imageService.downloadImageFile(userImage.getUserImage());
    }

    // ArtistName relation User --> Song
    public UserDto getArtistName(String artistName) {
        // Find the user by artistName
        User user = userRepository.findByArtistName(artistName)
                .orElseThrow(() -> new RecordNotFoundException("User not found for artistName: " + artistName));

        // Convert User entity to UserDto (if needed)
        UserDto userDto = convertUserToDto(user);

        return userDto;
    }

    public void updateArtistName(Long userId, String artistName) {
        Optional<User> optionalUser = userRepository.findByUserId(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setArtistName(artistName);
            userRepository.save(user);
        } else {
            throw new RecordNotFoundException("User not found with id: " + userId);
        }
    }

    // UserSong methods
    @Transactional
    public Resource getSongFromUser(Long userId) {
        Optional<User> optionalUser = userRepository.findByUserId(userId);
        if (optionalUser.isEmpty()) {
            throw new RecordNotFoundException("User " + userId + " not found. ");
        }
        Song song = (Song) optionalUser.get().getSongs();
        if (song == null) {
            throw new RecordNotFoundException("User " + userId + " has no Image");
        }
        return songService.downloadSongFile(song.getFileName());
    }

}
