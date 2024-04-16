package Journey_of_Taro_V3.Journey_of_Taro_V3.models.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.CustomMultipartFileController;
import Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.music.SongController;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.CustomMultipartFile;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.users.User;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music.SongRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.users.UserRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.music.SongServiceImpl;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.users.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SongTest {

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SongServiceImpl songService;

    @Mock
    private SongRepository songRepository;

    @InjectMocks
    private SongController songController;

    @InjectMocks
    private CustomMultipartFileController customMultipartFileController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    public class MockMultipartFileAdapter extends CustomMultipartFile {
        public MockMultipartFileAdapter(MockMultipartFile mockMultipartFile) throws IOException {
            super(mockMultipartFile.getOriginalFilename(), mockMultipartFile.getContentType(), mockMultipartFile.getBytes());
        }
    }

    @Test
    public void givenAudioFile_whenUpload_thenSuccess() throws IOException {
        // Arrange
        MultipartFile audioMockMultipartFile = new MockMultipartFile("file", "test_audio.mp3", "audio/mp3", "content".getBytes());
        SongDto songDto = new SongDto(); // SongCreateService create a dummy SongDto object for mocking response
        ResponseEntity<SongDto> responseEntity = ResponseEntity.ok(songDto);

        // Mocking song service
        when(songService.addSong(any())).thenReturn(responseEntity.getBody());

        // Act
        ResponseEntity<?> response = (ResponseEntity<?>) customMultipartFileController.fileUploadController(audioMockMultipartFile);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testSongCreation() throws IOException {
        byte[] fileContent = "This is a mock audio file content".getBytes();

        CustomMultipartFile customFile = new CustomMultipartFile("test_audio.mp3", "audio/mp3", fileContent);

        // Dummy SongDto object voor mocking
        SongDto songDto = new SongDto();

        ResponseEntity<SongDto> responseEntity = ResponseEntity.ok(songDto);

        // Mocking song service
        when(songService.addSong(any())).thenReturn(responseEntity.getBody());

        User user = new User();
        user.setUsername("testuser");

        ResponseEntity<SongDto> response = customMultipartFileController.addSong(customFile, "Song Title", user);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    // Single Song Tests
    @Test
    void testSongAttributesForSingleSong() throws IOException {
        // Mock the User object
        User artist = mock(User.class);
        // Set the username for the mocked User object
        when(artist.getUsername()).thenReturn("testuser");

        // Create a mock mp3 file
        byte[] fileContent = "Mock audio file content for single Song".getBytes();
        MockMultipartFile mp3File = new MockMultipartFile("file", "test_audio.mp3", "audio/mpeg", fileContent);

        // Adapt the MockMultipartFile to CustomMultipartFile
        CustomMultipartFile customMp3File = new MockMultipartFileAdapter(mp3File);

        // Create the Song object with the mocked User object and the custom mp3 file
        Song song = new Song("Single Song Test", customMp3File, artist, null); // Pass null for the songCollection parameter

        // Assertion
        assertEquals("Single Song Test", song.getSongTitle());
    }

    @Test
    void testAddSong() {
        // Arrange
        SongInputDto inputDto = new SongInputDto();
        inputDto.setArtistName("Test Artist 2");
        // Set other properties of inputDto as needed

        // Mock userRepository.findByArtistName
        User user = new User();
        user.setArtistName("Test Artist 2");
        when(userRepository.findByArtistName(eq("Test Artist 2"))).thenReturn(Optional.of(user));

        // Act
        SongDto result = songService.addSong(inputDto);

        // Assert
        assertNotNull(result);
        // Add more assertions as needed to validate the correctness of the result
    }

//    @Test
//    void testAddSong() {
//        // Arrange
//        CustomMultipartFile file = new CustomMultipartFile("test_audio.mp3", "audio/mp3", new byte[0]);
//        String songTitle = "Test Song";
//        String artistName = "Test Artist"; // Change artist name to "Test Artist"
//
//        // Create a mock User instance
//        User user = new User();
//        user.setArtistName(artistName);
//
//        // Mock behavior of userService and songService
//        when(userService.getUserByArtistName(eq(artistName))).thenReturn(Optional.of(user)); // Use "eq(artistName)" to match the argument
//        when(songService.addSong(any())).thenReturn(new SongDto(/* mock song data */));
//
//        // Act
//        ResponseEntity<SongDto> response = songController.addSong(artistName, file, songTitle);
//
//        // Assert
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertNotNull(response.getBody());
//    }

    @Test
    void testDeleteSong() {
        // Arrange
        Long id = 1L;

        // Act
        ResponseEntity<Object> response = songController.deleteSong(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testUpdateSong() {
        // Arrange
        Long id = 1L;
        SongInputDto inputDto = new SongInputDto(/* provide required data */);

        // Mock behavior of songService
        when(songService.updateSong(id, inputDto)).thenReturn(new SongDto(/* mock updated song data */));

        // Act
        ResponseEntity<Object> response = songController.updateSong(id, inputDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
