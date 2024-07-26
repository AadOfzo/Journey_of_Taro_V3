package Journey_of_Taro_V3.Journey_of_Taro_V3.services;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.users.UserDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.RecordNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.UserImage;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.Song;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.security.Authority;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.users.User;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music.SongRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.users.UserImageRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.users.UserRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.images.ImageServiceImpl;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.music.SongServiceImpl;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.users.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserImageRepository userImageRepository;

    @Mock
    private ImageServiceImpl imageService;

    @Mock
    private SongRepository songRepository;

    @Mock
    private SongServiceImpl songService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        UserDto userDto = new UserDto();
        userDto.setUsername("testuser");
        userDto.setPassword("password");
        userDto.setApikey("apikey");

        User user = new User();
        user.setUsername("testuser");
        user.setPassword("encodedPassword");
        user.setApikey("apikey");

        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        String username = userService.createUser(userDto);

        assertEquals("testuser", username);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testGetUsers() {
        User user = new User();
        user.setUsername("testuser");

        List<User> users = Collections.singletonList(user);
        when(userRepository.findAll()).thenReturn(users);

        List<UserDto> userDtos = userService.getUsers();

        assertNotNull(userDtos);
        assertEquals(1, userDtos.size());
        assertEquals("testuser", userDtos.get(0).getUsername());
    }

    @Test
    void testGetUserByUserName() {
        User user = new User();
        user.setUsername("testuser");

        when(userRepository.findById("testuser")).thenReturn(Optional.of(user));

        UserDto userDto = userService.getUserByUserName("testuser");

        assertNotNull(userDto);
        assertEquals("testuser", userDto.getUsername());
    }

    @Test
    void testGetUserByUserNameNotFound() {
        when(userRepository.findById("unknown")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.getUserByUserName("unknown"));
    }

    @Test
    void testGetUserById() {
        User user = new User();
        user.setUserId(1L);

        when(userRepository.findByUserId(1L)).thenReturn(Optional.of(user));

        UserDto userDto = userService.getUserById(1L);

        assertNotNull(userDto);
        assertEquals(1L, userDto.getUserId());
    }

    @Test
    void testGetUserByIdNotFound() {
        when(userRepository.findByUserId(1L)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void testGetUserByApiKey() {
        User user = new User();
        user.setApikey("apikey");

        when(userRepository.findByApikey("apikey")).thenReturn(user);

        UserDto userDto = userService.getUserByApiKey("apikey");

        assertNotNull(userDto);
        assertEquals("apikey", userDto.getApikey());
    }

    @Test
    void testConvertUserToDto() {
        User user = new User();
        user.setUsername("testuser");

        UserDto userDto = userService.convertUserToDto(user);

        assertNotNull(userDto);
        assertEquals("testuser", userDto.getUsername());
    }

    @Test
    void testFromUser() {
        User user = new User();
        user.setUsername("testuser");

        UserDto userDto = UserService.fromUser(user);

        assertNotNull(userDto);
        assertEquals("testuser", userDto.username);
    }

    @Test
    void testToUser() {
        UserDto userDto = new UserDto();
        userDto.setUsername("testuser");
        userDto.setPassword("password");

        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedPassword");

        User user = userService.toUser(userDto);

        assertNotNull(user);
        assertEquals("testuser", user.getUsername());
        assertEquals("encodedPassword", user.getPassword());
    }

    @Test
    void testDeleteUser() {
        userService.deleteUser("testuser");

        verify(userRepository).deleteById("testuser");
    }

    @Test
    void testUpdateUser() {
        User existingUser = new User();
        existingUser.setUserId(1L);
        existingUser.setUsername("olduser");

        UserDto newUserDto = new UserDto();
        newUserDto.setUsername("newuser");
        newUserDto.setPassword("newpassword");

        when(userRepository.findByUserId(1L)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode("newpassword")).thenReturn("encodedNewPassword");
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        UserDto updatedUserDto = userService.updateUser(1L, newUserDto);

        assertEquals("newuser", updatedUserDto.getUsername());
        assertEquals("encodedNewPassword", existingUser.getPassword());
    }

    @Test
    void testUpdateUserNotFound() {
        UserDto newUserDto = new UserDto();
        newUserDto.setUsername("newuser");

        when(userRepository.findByUserId(1L)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> userService.updateUser(1L, newUserDto));
    }

    @Test
    void testGetRoles() {
        User user = new User();
        Authority authority = new Authority(user, "ROLE_USER");
        user.setAuthorities(Collections.singleton(authority));

        when(userRepository.existsByUsername("testuser")).thenReturn(true);
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        Set<Authority> roles = userService.getRoles("testuser");

        assertNotNull(roles);
        assertEquals(1, roles.size());
        assertTrue(roles.stream().anyMatch(role -> role.getAuthority().equals("ROLE_USER")));
    }

    @Test
    void testAddRole() {
        // Arrange
        User user = new User();
        user.setUsername("testUser");
        user.setAuthorities(new HashSet<>());

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(userRepository.existsByUsername("testUser")).thenReturn(true);

        // Act
        userService.addRole("testUser", "ROLE_USER");

        // Assert
        Authority expectedAuthority = new Authority();
        expectedAuthority.setAuthority("ROLE_USER");
        expectedAuthority.setUser(user);

        boolean roleAdded = user.getAuthorities().stream()
                .anyMatch(a -> "ROLE_USER".equals(a.getAuthority()) && user.equals(a.getUser()));

        assertTrue(roleAdded);
    }


    @Test
    void testGrantAdminPrivilege() {
        User user = new User();

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        userService.grantAdminPrivilege("testuser");

        assertTrue(user.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    void testAssignImageToUser() {
        User user = new User();
        UserImage userImage = new UserImage();

        when(userRepository.findByUserId(1L)).thenReturn(Optional.of(user));
        when(userImageRepository.findUserImageByImageName("image")).thenReturn(Optional.of(userImage));
        when(userRepository.save(user)).thenReturn(user);

        User updatedUser = userService.assignImageToUser(1L, "image");

        assertEquals(userImage, updatedUser.getUserImage());
    }

    @Test
    void testGetImageFromUser() {
        User user = new User();
        UserImage userImage = new UserImage();
        user.setUserImage(userImage);

        when(userRepository.findByUserId(1L)).thenReturn(Optional.of(user));
        when(imageService.downloadImageFile(userImage.getUserImageName())).thenReturn(mock(Resource.class));

        Resource resource = userService.getImageFromUser(1L);

        assertNotNull(resource);
    }

    @Test
    void testGetArtistName() {
        User user = new User();
        user.setArtistName("artist");

        when(userRepository.findByArtistName("artist")).thenReturn(Optional.of(user));

        UserDto userDto = userService.getArtistName("artist");

        assertEquals("artist", userDto.getArtistname());
    }

    @Test
    void testUpdateArtistName() {
        User user = new User();
        user.setUserId(1L);

        when(userRepository.findByUserId(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        userService.updateArtistName(1L, "newArtist");

        assertEquals("newArtist", user.getArtistName());
    }

    @Test
    void testGetSongFromUser() {
        User user = new User();
        Song song = new Song();
        song.setFileName("test_song.mp3");
        user.setSongs(Collections.singletonList(song)); // Set a list of songs

        when(userRepository.findByUserId(1L)).thenReturn(Optional.of(user));
        when(songService.downloadSongFile("test_song.mp3")).thenReturn(mock(Resource.class));

        Resource resource = userService.getSongFromUser(1L);

        assertNotNull(resource);
    }


}

