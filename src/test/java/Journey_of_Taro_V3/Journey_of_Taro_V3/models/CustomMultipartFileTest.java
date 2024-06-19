package Journey_of_Taro_V3.Journey_of_Taro_V3.models;

import Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.CustomMultipartFileController;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.images.ImageService;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.music.SongService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomMultipartFileTest {

    @InjectMocks
    private CustomMultipartFileController controller;

    @Mock
    private ImageService imageService;

    @Mock
    private SongService songService;

    @Mock(lenient = true)
    private Environment environment;

    @BeforeEach
    public void setUp() {
        controller = new CustomMultipartFileController(imageService, songService, environment);
        when(environment.getProperty("base.url", "http://localhost:8080")).thenReturn("http://localhost:8080");
    }

    @Test
    public void whenProvidingByteArray_thenMultipartFileCreated() throws IOException {
        // Arrange
        byte[] inputArray = "Test String".getBytes();

        // Act
        CustomMultipartFile customMultipartFile = new CustomMultipartFile("filename.txt", "text/plain", inputArray);

        // Assert
        assertFalse(customMultipartFile.isEmpty());
        assertArrayEquals(inputArray, customMultipartFile.getBytes());
        assertEquals(inputArray.length, customMultipartFile.getSize());
    }

    @Test
    public void whenProvidingByteArray_thenMockMultipartFileCreated() throws IOException {
        // Arrange
        byte[] inputArray = "Test String".getBytes();

        // Act
        MockMultipartFile mockMultipartFile = new MockMultipartFile("tempFileName", inputArray);

        // Assert
        assertFalse(mockMultipartFile.isEmpty());
        assertArrayEquals(inputArray, mockMultipartFile.getBytes());
        assertEquals(inputArray.length, mockMultipartFile.getSize());
    }

    @Test
    public void testDetermineFileType_Image() throws IOException {
        // Arrange
        byte[] imageData = "Sample image data".getBytes();
        MultipartFile multipartFile = new MockMultipartFile("imageFile", "image.jpg", "image/jpeg", imageData);

        // Act
        String result = controller.fileUploadController(multipartFile, null, null, null).getStatusCode().toString();

        // Assert
        assertEquals("200 OK", result);
    }

    @Test
    public void testDetermineFileType_Audio() throws IOException {
        // Arrange
        byte[] audioData = "Sample audio data".getBytes();
        MultipartFile multipartFile = new MockMultipartFile("audioFile", "audio.mp3", "audio/mpeg", audioData);

        // Act
        String result = controller.fileUploadController(multipartFile, null, null, null).getStatusCode().toString();

        // Assert
        assertEquals("200 OK", result);
    }

    @Test
    public void testCustomMultipartFile() throws IOException {
        // Arrange
        String originalFilename = "test_song.mp3";
        String contentType = "audio/mpeg";
        byte[] fileContent = {1, 2, 3, 4, 5};

        // Act
        MultipartFile multipartFile = new CustomMultipartFile(originalFilename, contentType, fileContent);

        // Assert
        assertEquals("file", multipartFile.getName());
        assertEquals(originalFilename, multipartFile.getOriginalFilename());
        assertEquals(contentType, multipartFile.getContentType());
        assertFalse(multipartFile.isEmpty());
        assertEquals(fileContent.length, multipartFile.getSize());

        byte[] content = multipartFile.getBytes();
        assertArrayEquals(fileContent, content);

        InputStream inputStream = multipartFile.getInputStream();
        byte[] buffer = new byte[1024];
        int bytesRead = inputStream.read(buffer);
        assertEquals(fileContent.length, bytesRead);
        for (int i = 0; i < fileContent.length; i++) {
            assertEquals(fileContent[i], buffer[i]);
        }

        File destinationFile = new File("destination_file.mp3");
        multipartFile.transferTo(destinationFile);
        byte[] transferredContent = Files.readAllBytes(destinationFile.toPath());
        assertArrayEquals(fileContent, transferredContent);

        destinationFile.delete();
    }
}
