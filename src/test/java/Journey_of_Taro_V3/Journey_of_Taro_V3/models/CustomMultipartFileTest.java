package Journey_of_Taro_V3.Journey_of_Taro_V3.models;

import Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.CustomMultipartFileController;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.images.ImageService;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.music.SongService;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import java.io.IOException;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomMultipartFileTest {

    @InjectMocks
    private CustomMultipartFileController customMultipartFileController;

    @Mock
    private ImageService imageService;

    @Mock
    private SongService songService;

    @Mock
    private Environment environment;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(environment.getProperty("base.url", "http://localhost:8080")).thenReturn("http://localhost:8080");
    }

    @Test
    public void testImageUploadSuccess() throws IOException {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("file", "image1.jpg", "image/jpeg", "test image data".getBytes());
        ImageDto imageDto = new ImageDto();
        when(imageService.addImage(any(ImageInputDto.class))).thenReturn(imageDto);

        // Act
        ResponseEntity<?> result = customMultipartFileController.fileUploadController(file, null, null);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertTrue(result.getBody() instanceof ImageDto);
        verify(imageService).addImage(any(ImageInputDto.class));
    }

    @Test
    public void testAudioUploadSuccess() throws IOException {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("file", "song.mp3", "audio/mpeg", "test audio data".getBytes());
        SongDto songDto = new SongDto();
        when(songService.addSong(any(SongInputDto.class))).thenReturn(songDto);

        // Act
        ResponseEntity<?> result = customMultipartFileController.fileUploadController(file, "Artist Name", "Song Title");

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertTrue(result.getBody() instanceof SongDto);
        verify(songService).addSong(any(SongInputDto.class));
    }

    @Test
    public void testFileUploadWithEmptyFile() throws IOException {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("file", "", "image/jpeg", new byte[0]);

        // Act
        ResponseEntity<?> result = customMultipartFileController.fileUploadController(file, null, null);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        assertEquals("File is empty", result.getBody());
    }

    @Test
    public void testUnsupportedFileType() throws IOException {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("file", "file.txt", "text/plain", "test data".getBytes());

        // Act
        ResponseEntity<?> result = customMultipartFileController.fileUploadController(file, null, null);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        assertEquals("Unsupported file type", result.getBody());
    }
}
