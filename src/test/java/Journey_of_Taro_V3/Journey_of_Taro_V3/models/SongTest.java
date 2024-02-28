package Journey_of_Taro_V3.Journey_of_Taro_V3.models;

import Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.CustomMultipartFileController;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.Song;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.SongCollectionType;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.users.User;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music.SongRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.users.UserRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.music.SongService;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.music.SongServiceImpl;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SongTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SongServiceImpl songService;

    @Mock
    private SongRepository songRepository;

    @InjectMocks
    private CustomMultipartFileController customMultipartFileController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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

        // Mocking the behavior of the song service
        when(songService.addSong(any())).thenReturn(responseEntity.getBody());

        // Act
        ResponseEntity<?> response = (ResponseEntity<?>) customMultipartFileController.fileUploadController(audioMockMultipartFile);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testSongCreation() throws IOException {
        // Given
        byte[] fileContent = "This is a mock audio file content".getBytes();

        // SongCreateService a CustomMultipartFile
        CustomMultipartFile customFile = new CustomMultipartFile("test_audio.mp3", "audio/mp3", fileContent);

        // SongCreateService a dummy SongDto object voor mocking
        SongDto songDto = new SongDto();

        ResponseEntity<SongDto> responseEntity = ResponseEntity.ok(songDto);

        // Mocking the behavior of the song service
        when(songService.addSong(any())).thenReturn(responseEntity.getBody());

        User user = new User();
        user.setUsername("testuser");

        // When
        ResponseEntity<SongDto> response = customMultipartFileController.addSong(customFile, "Song Title", user);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        // Add more assertions if needed
    }

    // Single "songs" Tests
    @Test
    void testSongAttributesForDemos() throws IOException {
        // Mock the User object
        User artist = mock(User.class);
        // Set the username for the mocked User object
        when(artist.getUsername()).thenReturn("testuser");

        // SongCreateService a mock mp3 file
        byte[] fileContent = "Mock audio file content for: Demos".getBytes();
        MockMultipartFile mp3File = new MockMultipartFile("file", "test_audio.mp3", "audio/mpeg", fileContent);

        // Adapt the MockMultipartFile to CustomMultipartFile
        CustomMultipartFile customMp3File = new MockMultipartFileAdapter(mp3File);

        // SongCreateService the Song object with the mocked User object and the custom mp3 file
        Song song = new Song("Singles Song", customMp3File, artist, SongCollectionType.Demos);

        // Assertions
        assertEquals("Singles Song", song.getSongTitle());
        assertEquals(artist, song.getArtistName());
        assertEquals(SongCollectionType.Demos, song.getSongCollectionType());
    }


}
