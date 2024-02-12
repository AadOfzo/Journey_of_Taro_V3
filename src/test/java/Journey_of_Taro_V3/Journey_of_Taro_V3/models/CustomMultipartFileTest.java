package Journey_of_Taro_V3.Journey_of_Taro_V3.models;

import Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.CustomMultipartFileController;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.Image;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.Song;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.music.SongService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CustomMultipartFileTest {
    @InjectMocks
    private CustomMultipartFileController controller;

    @Mock
    private SongService songService;

    @BeforeEach
    public void setUp() {
        // Setup any necessary behavior for the mocked SongService if needed
    }

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
    public void testDetermineFileType_Image() throws IOException {
        // Arrange
        byte[] fileContent = "test content".getBytes();
        MultipartFile multipartFile = new MockMultipartFile("test.jpg", fileContent);

        // Create a mock SongService
        SongService songServiceMock = Mockito.mock(SongService.class);

        // Initialize the controller with the mock SongService
        CustomMultipartFileController controller = new CustomMultipartFileController(songServiceMock);

        // Act
        Object result = controller.fileUploadController(multipartFile);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(Image.class, result.getClass(), "Uploaded image file should create an Image object");
    }

    @Test
    public void testDetermineFileType_Audio() throws IOException {
        // Arrange
        byte[] fileContent = "test content".getBytes();
        MultipartFile multipartFile = new MockMultipartFile("test.mp3", fileContent);

        // Create a mock SongService
        SongService songServiceMock = Mockito.mock(SongService.class);

        // Initialize the controller with the mock SongService
        CustomMultipartFileController controller = new CustomMultipartFileController(songServiceMock);

        // Act
        Object result = controller.fileUploadController(multipartFile);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(Song.class, result.getClass(), "Uploaded audio file should create a Song object");
    }


}



