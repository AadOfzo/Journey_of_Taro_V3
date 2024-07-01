package Journey_of_Taro_V3.Journey_of_Taro_V3.services.users;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.users.UserDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.RecordNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.UserSong;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.security.Authority;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.users.User;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.UserImage;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.images.UserImageRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music.UserSongRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.users.UserRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.images.ImageServiceImpl;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.music.SongServiceImpl;
import Journey_of_Taro_V3.Journey_of_Taro_V3.utils.RandomStringGenerator;
import org.springframework.core.io.Resource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserImageRepository userImageRepository;
    private final ImageServiceImpl imageService;
    private final UserSongRepository userSongRepository;
    private final SongServiceImpl songService;

    public UserService(UserRepository userRepository, UserImageRepository userImageRepository, ImageServiceImpl imageService, UserSongRepository userSongRepository, SongServiceImpl songService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userImageRepository = userImageRepository;
        this.imageService = imageService;
        this.userSongRepository = userSongRepository;
        this.songService = songService;
    }

    public UserDto getUserByApiKey(String apikey) {
        User user = userRepository.findByApikey(apikey);
        return user != null ? fromUser(user) : null;
    }

    public List<UserDto> getUsers() {
        return userRepository.findAll().stream().map(UserService::fromUser).collect(Collectors.toList());
    }

    public UserDto getUser(String username) {
        return userRepository.findById(username).map(UserService::fromUser).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public User saveUser(UserDto userDto) {
        return userRepository.save(toUser(userDto));
    }

    public String createUser(UserDto userDto) {
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        userDto.setApikey(randomString);
        return userRepository.save(toUser(userDto)).getUsername();
    }

    public UserDto getArtistName(String artistName) {
        User user = userRepository.findByArtistName(artistName).orElseThrow(() -> new RecordNotFoundException("User not found for artistName: " + artistName));
        return fromUser(user);
    }

    public void updateUser(String username, UserDto dto) {
        User user = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException(username));
        user.setFirstName(dto.getFirstname());
        user.setLastName(dto.getLastname());
        user.setDateOfBirth(dto.getDateofbirth());
        user.setEmail(dto.getEmail());
        user.setCountry(dto.getCountry());
        userRepository.save(user);
    }

    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }

    public List<String> getRoles(String username) {
        User user = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new ArrayList<>(user.getRoles());
    }

    public void addRole(String username, String roleName) {
        User user = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException(username));
        user.addRole(roleName);
        userRepository.save(user);
    }

    @Transactional
    public void grantAdminPrivilege(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        Authority adminAuthority = new Authority(user, "ROLE_ADMIN");

        user.addAuthorities(adminAuthority);

        userRepository.save(user);
    }

    public void updateArtistName(Long userId, String artistName) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException(userId.toString()));
        user.setArtistName(artistName);
        userRepository.save(user);
    }

    // User Files: Images en Songs.
    public User addImageToUser(MultipartFile imageFile, String apikey) {
        User user = userRepository.findByApikey(apikey);
        if (user == null) {
            throw new RecordNotFoundException("User not found for apikey: " + apikey);
        }

        try {
            String imagePath = imageService.storeImageFile(imageFile);
            UserImage userImage = new UserImage(imageFile.getOriginalFilename(), imagePath);
            userImageRepository.save(userImage);
            user.setUserImage(userImage);
            return userRepository.save(user);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store image file", e);
        }
    }

    public User addSongToUser(MultipartFile songFile, String apikey) {
        User user = userRepository.findByApikey(apikey);
        try {
            UserSong userSong = new UserSong(songFile.getOriginalFilename(), songService.storeSongFile(songFile));
            userSongRepository.save(userSong);
            user.setUserSong(userSong);
            return userRepository.save(user);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store song file", e);
        }
    }

    public Resource getImageFromUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()){
            throw new RecordNotFoundException("User with user-id: " + userId + "not found.");
        }
        UserImage userImage = optionalUser.get().getUserImage();
        if (userImage == null){
            throw new RecordNotFoundException("User " + userId + "has no image.");
        }
        return imageService.downloadImageFile(userImage.getImagePath());
    }

//    public Resource getImageFromUser(Long userId) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new RecordNotFoundException("User not found"));
//        return imageService.loadAsResource(user.getUserImage().getImageName());
//    }

    @Transactional
    public Resource getSongFromUser(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new RecordNotFoundException("User " + userName + " not found."));

        UserSong userSong = user.getUserSong();
        if (userSong == null) {
            throw new RecordNotFoundException("User " + userName + " has no Song");
        }
        return songService.downloadSongFile(userSong.getSongTitle());
    }

    public static UserDto fromUser(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setApikey(user.getApikey());
        dto.setFirstname(user.getFirstName());
        dto.setLastname(user.getLastName());
        dto.setDateofbirth(user.getDateOfBirth());
        dto.setEmail(user.getEmail());
        dto.setCountry(user.getCountry());
        dto.setUserimage(user.getUserImage());
        dto.setUserSong(user.getUserSong());
        dto.setArtistname(user.getArtistName());
        dto.setRoles(new ArrayList<>(user.getRoles()));
        return dto;
    }

    private static User toUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setApikey(userDto.getApikey());
        user.setFirstName(userDto.getFirstname());
        user.setLastName(userDto.getLastname());
        user.setDateOfBirth(userDto.getDateofbirth());
        user.setEmail(userDto.getEmail());
        user.setCountry(userDto.getCountry());
        user.setUserImage(userDto.getUserimage());
        user.setUserSong(userDto.getUserSong());
        user.setArtistName(userDto.getArtistname());
        user.setRoles(new HashSet<>(userDto.getRoles()));
        return user;
    }

}
