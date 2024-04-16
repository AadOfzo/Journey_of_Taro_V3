package Journey_of_Taro_V3.Journey_of_Taro_V3.models;

import Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.CustomMultipartFileController;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.images.ImageService;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.music.SongService;
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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

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
    public void testDetermineFileType_Image() throws IOException {
        // Arrange
        byte[] imageData = "Sample image data".getBytes();
        MultipartFile multipartFile = new MockMultipartFile("imageFile", "image.jpg", "image/jpeg", imageData);

        ImageService imageService = mock(ImageService.class);
        SongService songService = mock(SongService.class); // Add mock for SongService

        CustomMultipartFileController controller = new CustomMultipartFileController(imageService, songService);

        // Act
        String result = controller.determineFileType(multipartFile.getContentType()); // Pass content type as argument

        // Assert
        assertEquals("image", result.toLowerCase());
    }

    @Test
    public void testDetermineFileType_Audio() {
        // Arrange
        byte[] audioData = "Sample audio data".getBytes(); // Mocked audio data
        MultipartFile multipartFile = new MockMultipartFile("audioFile", "audio.mp3", "audio/mpeg", audioData);

        // SongCreateService a mock for SongService
        SongService songService = mock(SongService.class);

        // Instantiate the controller with the mocked SongService
        CustomMultipartFileController controller = new CustomMultipartFileController(null, songService);

        // Act
        String result = controller.determineFileType(multipartFile.getContentType()); // Pass content type as argument

        // Assert
        assertEquals("audio", result.toLowerCase()); // Ignore case when comparing
    }

    @Test
    public void testCustomMultipartFile() {
        // Prepare test data
        String originalFilename = "test_song.mp3";
        String contentType = "audio/mpeg";
        byte[] fileContent = {1, 2, 3, 4, 5}; // Sample file content

        // Create a CustomMultipartFile instance
        MultipartFile multipartFile = new CustomMultipartFile(originalFilename, contentType, fileContent);

        // Test methods of CustomMultipartFile
        assertEquals("file", multipartFile.getName());
        assertEquals(originalFilename, multipartFile.getOriginalFilename());
        assertEquals(contentType, multipartFile.getContentType());
        assertTrue(!multipartFile.isEmpty());
        assertEquals(fileContent.length, multipartFile.getSize());

        try {
            byte[] content = multipartFile.getBytes();
            assertEquals(fileContent.length, content.length);
            for (int i = 0; i < fileContent.length; i++) {
                assertEquals(fileContent[i], content[i]);
            }

            // Test InputStream
            InputStream inputStream = multipartFile.getInputStream();
            byte[] buffer = new byte[1024];
            int bytesRead = inputStream.read(buffer);
            assertEquals(fileContent.length, bytesRead);
            for (int i = 0; i < fileContent.length; i++) {
                assertEquals(fileContent[i], buffer[i]);
            }

            // Test transferTo
            File destinationFile = new File("destination_file.mp3");
            multipartFile.transferTo(destinationFile);
            byte[] transferredContent = Files.readAllBytes(destinationFile.toPath());
            assertEquals(fileContent.length, transferredContent.length);
            for (int i = 0; i < fileContent.length; i++) {
                assertEquals(fileContent[i], transferredContent[i]);
            }
            // Clean up
            destinationFile.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}



