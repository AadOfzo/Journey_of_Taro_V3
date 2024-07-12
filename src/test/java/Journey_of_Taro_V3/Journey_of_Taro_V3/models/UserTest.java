package Journey_of_Taro_V3.Journey_of_Taro_V3.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.*;

import Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.users.UserController;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.users.UserDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.users.User;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.images.ImageServiceImpl;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.music.SongServiceImpl;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.users.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class UserTest {

    @Mock
    private UserService userService;

    @Mock
    private ImageServiceImpl imageService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    // Test for GET /users
    @Test
    public void testGetUsers() {
        // Mock data
        List<UserDto> users = Arrays.asList(
                createUserDto(1L, "user1", "password1", "apikey1", "User1", "Lastname1", new Date(), "Country1", "user1@example.com"),
                createUserDto(2L, "user2", "password2", "apikey2", "User2", "Lastname2", new Date(), "Country2", "user2@example.com")
        );
        when(userService.getUsers()).thenReturn(users);

        // Call controller method
        ResponseEntity<List<UserDto>> response = userController.getUsers();

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users.size(), Objects.requireNonNull(response.getBody()).size());
        assertEquals(users.get(0).getUsername(), response.getBody().get(0).getUsername());
        assertEquals(users.get(1).getUsername(), response.getBody().get(1).getUsername());
    }

    // Test for GET /users/{username}
    @Test
    public void testGetUserByUsername() {
        // Mock data
        String username = "user1";
        UserDto userDto = createUserDto(1L, username, "password1", "apikey1", "User1", "Lastname1", new Date(), "Country1", "user1@example.com");
        when(userService.getUserByUserName(username)).thenReturn(userDto);

        // Call controller method
        ResponseEntity<UserDto> response = userController.getUser(username);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(username, Objects.requireNonNull(response.getBody()).getUsername());
    }

    // Test for POST /users/{id}/image
    @Test
    public void testAddImageToUser() throws Exception {
        // Mock data
        Long userId = 1L;
        String imageUrl = "/users/1/image";
        String fileName = "test-image.jpg";
        User user = new User();
        user.setUserId(userId);

        // Mock HttpServletRequest
        when(request.getRequestURL()).thenReturn(new StringBuffer(imageUrl));

        // Mock MultipartFile
        byte[] fileBytes = "test image data".getBytes();
        MultipartFile multipartFile = new MockMultipartFile("file", fileName, "image/jpeg", fileBytes);
        when(imageService.storeFile(any(MultipartFile.class))).thenReturn(fileName);
        when(userService.assignImageToUser(eq(fileName), eq(userId))).thenReturn(user);

        // Mock ServletUriComponentsBuilder
        URI uri = new URI(imageUrl);
        ServletUriComponentsBuilder servletUriComponentsBuilder = mock(ServletUriComponentsBuilder.class);
        when(ServletUriComponentsBuilder.fromCurrentContextPath()).thenReturn(servletUriComponentsBuilder);
        when(servletUriComponentsBuilder.path(any())).thenReturn(servletUriComponentsBuilder);
        when(servletUriComponentsBuilder.buildAndExpand(any(Map.class))).thenAnswer(invocation -> uri); // Use thenAnswer to return a URI

        // Call controller method
        ResponseEntity<User> response = userController.addImageToUser(userId, multipartFile);

        // Verify
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user.getUserId(), Objects.requireNonNull(response.getBody()).getUserId());
        assertTrue(response.getHeaders().containsKey(HttpHeaders.LOCATION));
        assertEquals(uri, response.getHeaders().getLocation()); // Compare with the URI object
    }

    // Helper method to create UserDto instances
    private UserDto createUserDto(Long userId, String username, String password, String apiKey, String firstname, String lastname, Date dateOfBirth, String country, String email) {
        UserDto userDto = new UserDto();
        userDto.setUserId(userId);
        userDto.setUsername(username);
        userDto.setPassword(password);
        userDto.setApikey(apiKey);
        userDto.setFirstname(firstname);
        userDto.setLastname(lastname);
        userDto.setDateofbirth(dateOfBirth);
        userDto.setCountry(country);
        userDto.setEmail(email);
        return userDto;
    }
}