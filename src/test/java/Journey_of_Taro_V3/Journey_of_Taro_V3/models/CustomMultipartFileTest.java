package Journey_of_Taro_V3.Journey_of_Taro_V3.models;

import Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.CustomMultipartFileController;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.images.ImageService;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.music.SongService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import java.io.IOException;

@ExtendWith(MockitoExtension.class)
public class CustomMultipartFileTest {

    @InjectMocks
    private CustomMultipartFileController controller;

    @Mock
    private ImageService imageService;

    @Mock
    private SongService songService;

    @BeforeEach
    public void setUp() {
        // Setup any necessary behavior for the mocked SongService if needed
    }

    public class MockMultipartFileAdapter extends CustomMultipartFile {
        public MockMultipartFileAdapter(MockMultipartFile mockMultipartFile) throws IOException {
            super(mockMultipartFile.getOriginalFilename(), mockMultipartFile.getContentType(), mockMultipartFile.getBytes());
        }
    }

    // Voorbeeld gehaald van:
    // https://www.baeldung.com/java-convert-byte-array-to-multipartfile
    @Test
    public void whenProvidingByteArray_thenMultipartFileCreated() throws IOException {
        byte[] inputArray = "Test String".getBytes();
        CustomMultipartFile customMultipartFile = new CustomMultipartFile("filename.txt", "text/plain", inputArray);
        assertFalse(customMultipartFile.isEmpty());
        assertArrayEquals(inputArray, customMultipartFile.getBytes());
        assertEquals(inputArray.length, customMultipartFile.getSize());
    }

    @Test
    public void whenProvidingByteArray_thenMockMultipartFileCreated() throws IOException {
        byte[] inputArray = "Test String".getBytes();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("tempFileName", inputArray);
        Assertions.assertFalse(mockMultipartFile.isEmpty());
        Assertions.assertArrayEquals(inputArray, mockMultipartFile.getBytes());
        Assertions.assertEquals(inputArray.length, mockMultipartFile.getSize());
    }

    @Test
    public void testDetermineFileType_Image() {
        // Arrange
        byte[] imageData = "Sample image data".getBytes();
        MultipartFile multipartFile = new MockMultipartFile("imageFile", "image.jpg", "image/jpeg", imageData);

        // Act
        String result = controller.determineFileType(multipartFile.getContentType()); // Pass content type as argument

        // Assert
        // Add your assertions here based on the expected behavior
    }

    @Test
    public void testDetermineFileType_Audio() {
        // Arrange
        byte[] audioData = "Sample audio data".getBytes(); // Mocked audio data
        MultipartFile multipartFile = new MockMultipartFile("audioFile", "audio.mp3", "audio/mpeg", audioData);

        // Create a mock for SongService
        SongService songService = mock(SongService.class);

        // Instantiate the controller with the mocked SongService
        CustomMultipartFileController controller = new CustomMultipartFileController(null, songService);

        // Act
        String result = controller.determineFileType(multipartFile.getContentType()); // Pass content type as argument

        // Assert
        assertEquals("audio", result.toLowerCase()); // Ignore case when comparing
    }

}



