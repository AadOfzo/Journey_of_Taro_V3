package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.users;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.users.UserDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.images.ImageServiceImpl;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.music.SongServiceImpl;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.users.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class UserControllerTest {

    private UserService userService;
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = mock(UserService.class);
        ImageServiceImpl imageService = mock(ImageServiceImpl.class);
        SongServiceImpl songService = mock(SongServiceImpl.class);
        userController = new UserController(userService, imageService, songService);
    }

    @Test
    void testGetUsers() {
        // Mock data
        UserDto userDto = new UserDto();
        userDto.setUsername("testUser");
        when(userService.getUsers()).thenReturn(Collections.singletonList(userDto));

        // Call controller method
        ResponseEntity<List<UserDto>> responseEntity = userController.getUsers();

        // Verify response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, Objects.requireNonNull(responseEntity.getBody()).size());
        assertEquals("testUser", responseEntity.getBody().get(0).getUsername());

        // Verify service method was called
        verify(userService).getUsers();
    }

    @Test
    void testCreateUser() {
        UserDto userDto = new UserDto();
        userDto.setUsername("testUser");
        when(userService.createUser(userDto)).thenReturn("testUser");

        ResponseEntity<UserDto> responseEntity = userController.createUser(userDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(URI.create("/users/testUser"), responseEntity.getHeaders().getLocation());

        verify(userService).createUser(userDto);
    }

    @Test
    void testGetUser() {
        UserDto userDto = new UserDto();
        userDto.setUsername("testUser");
        when(userService.getUser("testUser")).thenReturn(userDto);

        ResponseEntity<UserDto> responseEntity = userController.getUser("testUser");

        // Verify response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("testUser", Objects.requireNonNull(responseEntity.getBody()).getUsername());

        // Verify service method was called
        verify(userService).getUser("testUser");
    }
}
