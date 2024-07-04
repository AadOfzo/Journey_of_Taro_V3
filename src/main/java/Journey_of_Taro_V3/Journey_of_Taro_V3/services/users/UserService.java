package Journey_of_Taro_V3.Journey_of_Taro_V3.services.users;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.users.UserDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.RecordNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.Song;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.UserSong;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.security.Authority;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.users.User;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.UserImage;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.images.ImageRepository;
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
    private final ImageRepository imageRepository;
    private final ImageServiceImpl imageService;
    private final SongRepository songRepository;
    private final SongServiceImpl songService;
    private final PasswordEncoder passwordEncoder;

    // TODO: 29/02/2024 Could not autowire PasswordEncoder code werkt wel. Users kunnen aangemaakt worden.
    // https://www.baeldung.com/spring-security-registration-password-encoding-bcrypt


    public UserService(UserRepository userRepository, ImageRepository imageRepository, ImageServiceImpl imageService, SongRepository songRepository, SongServiceImpl songService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
        this.imageService = imageService;
        this.songRepository = songRepository;
        this.songService = songService;
        this.passwordEncoder = passwordEncoder;
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
        UserDto dto;
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

    public UserDto getArtistName(String artistName) {
        // Find the user by artistName
        User user = userRepository.findByArtistName(artistName)
                .orElseThrow(() -> new RecordNotFoundException("User not found for artistName: " + artistName));

        // Convert User entity to UserDto (if needed)
        UserDto userDto = convertUserToDto(user);

        return userDto;
    }
    public UserDto convertUserToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
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

        // Bepaal user's roles gebaseerd op authorities
        List<String> roles = user.getAuthorities().stream()
                .map(authority -> authority.getAuthority().substring(5)) // verwijder "ROLE_" prefix
                .collect(Collectors.toList());
        userDto.setRoles(roles);

        return userDto;
    }

    public static UserDto fromUser(User user) {

        var dto = new UserDto();

        dto.id = user.getId();
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

//    public void updateUser(String username, UserDto newUser) {
//        if (!userRepository.existsByUsername(username)) throw new RecordNotFoundException();
//        User user = userRepository.findById(username).orElseThrow(() -> new RecordNotFoundException("User not found with username: " + username));
//        user.setPassword(passwordEncoder.encode(newUser.getPassword())); // Encode the password before setting
//        userRepository.save(user);
//    }

    public User updateUser(String username, UserDto newUser) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RecordNotFoundException("User not found with username: " + username));

        if (newUser.getPassword() != null && !newUser.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        }
        if (newUser.getUsername() != null && !newUser.getUsername().isEmpty()) {
            user.setUsername(newUser.getUsername());
        }
        if (newUser.getFirstname() != null && !newUser.getFirstname().isEmpty()) {
            user.setFirstName(newUser.getFirstname());
        }
        if (newUser.getLastname() != null && !newUser.getLastname().isEmpty()) {
            user.setLastName(newUser.getLastname());
        }
        if (newUser.getDateofbirth() != null) {
            user.setDateOfBirth(newUser.getDateofbirth());
        }
        if (newUser.getCountry() != null && !newUser.getCountry().isEmpty()) {
            user.setCountry(newUser.getCountry());
        }
        if (newUser.getEmail() != null && !newUser.getEmail().isEmpty()) {
            user.setEmail(newUser.getEmail());
        }
        if (newUser.getUserimage() != null) {
            user.setUserImage(newUser.getUserimage());
        }
        if (newUser.getUserSong() != null) {
            user.setUserSong(newUser.getUserSong());
        }
        if (newUser.getArtistname() != null && !newUser.getArtistname().isEmpty()) {
            user.setArtistName(newUser.getArtistname());
        }
        if (newUser.getRoles() != null && !newUser.getRoles().isEmpty()) {
            Set<Authority> authorities = new HashSet<>();
            for (String role : newUser.getRoles()) {
                authorities.add(new Authority(user, role));
            }
            user.setAuthorities(authorities);
        }

        return userRepository.save(user);
    }

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

    @Transactional
    public Resource getImageFromUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()){
            throw new RecordNotFoundException("User " + id + " not found. ");
        }
        UserImage userImage = optionalUser.get().getUserImage();
        if (userImage == null){
            throw new RecordNotFoundException("User " + id + " has no Image");
        }
        return imageService.downloadImageFile(userImage.getFileName());
    }

    public void updateArtistName(Long userId, String artistName) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setArtistName(artistName);
            userRepository.save(user);
        } else {
            throw new RecordNotFoundException("User not found with id: " + userId);
        }
    }

    @Transactional
    public User assignImageToUser(String apikey, String imageName) {
        Optional<User> optionalUser = userRepository.findById(apikey);
        Optional<UserImage> optionalUserImage = imageRepository.findImageByImageName(imageName);
        if (optionalUser.isPresent() && optionalUserImage.isPresent()) {
            UserImage userImage = optionalUserImage.get();
            User user = optionalUser.get();
            user.setUserImage(userImage);
            return userRepository.save(user);
        } else {
            throw new RecordNotFoundException("User with apikey " + apikey + " not found");
        }

    }

//    @Transactional
//    public User assignSongToUser(String apikey, String songTitle) {
//        Optional<User> optionalUser = userRepository.findById(apikey);
//        Optional<UserSong> optionalUserSong = songRepository.findBySongTitle(songTitle);
//    }

    @Transactional
    public Resource getSongFromUser(String userName) {
        Optional<User> optionalUser = userRepository.findByUsername(userName);
        if (optionalUser.isEmpty()){
            throw new RecordNotFoundException("User " + userName + " not found. ");
        }
        Song song = (Song) optionalUser.get().getSongs();
        if (song == null){
            throw new RecordNotFoundException("User " + userName + " has no Image");
        }
        return songService.downloadSongFile(song.getFileName());
    }


}
