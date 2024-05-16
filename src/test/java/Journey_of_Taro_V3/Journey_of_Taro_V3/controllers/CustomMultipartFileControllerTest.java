package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.images.ImageRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music.SongRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.users.UserRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.images.ImageService;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.music.SongService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomMultipartFileController.class)
class CustomMultipartFileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;
    private ImageService imageService = mock(ImageService.class);
    @MockBean
    private ImageRepository imageRepository;
    private final SongService songService = mock(SongService.class);
    @MockBean
    private SongRepository songRepository;

    private final CustomMultipartFileController multipartFileController = new CustomMultipartFileController(imageService, songService);

    @Test
    public void testFileUploadController() throws Exception {
        // Mock MultipartFile
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Test file content".getBytes());

        // Mock the behavior of determineFileType method
        given(determineFileType("text/plain")).willReturn("Unknown");

        // Perform the request
        mockMvc.perform(MockMvcRequestBuilders.multipart("/fileUpload")
                        .file(file))
                .andExpect(status().isOk());

        // You can add more assertions based on the expected behavior of your controller
    }

    // Mock the determineFileType method
    private String determineFileType(String contentType) {
        if (contentType != null && contentType.startsWith("image")) {
            return "Image";
        } else if (contentType != null && contentType.startsWith("audio")) {
            return "Audio";
        } else {
            return "Unknown";
        }
    }

    @Test
    void testFileUploadController_Image() throws IOException{
        // Arrange
        String fileName = "testImage.png";
        byte[] fileContent = "Test image content".getBytes();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", fileName, "image/png", fileContent);

        ImageDto imageDto = new ImageDto();
        when(imageService.addImage(any())).thenReturn(imageDto);

        // Act
        ResponseEntity<?> responseEntity = multipartFileController.fileUploadController(mockMultipartFile);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(ImageDto.class, responseEntity.getBody().getClass());
    }

    @Test
    void testFileUploadController_Audio() throws IOException {
        // Arrange
        String fileName = "testAudio.mp3";
        byte[] fileContent = "Test audio content".getBytes();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", fileName, "audio/mpeg", fileContent);

        SongDto songDto = new SongDto();
        when(songService.addSong(any())).thenReturn(songDto);

        // Act
        ResponseEntity<?> responseEntity = multipartFileController.fileUploadController(mockMultipartFile);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(SongDto.class, responseEntity.getBody().getClass());
    }

    // TODO: 08/03/2024 Test UnsupportedFileType fails
    @Test
    public void testFileUploadController_UnsupportedFileType() throws Exception {
        // Create a mock MultipartFile with unsupported file type (e.g., .txt)
        MockMultipartFile file = new MockMultipartFile("testFile.txt", "testFile.txt", "text/plain", "test data".getBytes());

        // Perform a POST request to "/fileUpload" with the mock file
        mockMvc.perform(MockMvcRequestBuilders.multipart("/fileUpload")
                        .file("file", file.getBytes()))
                .andExpect(status().isInternalServerError()); // Assert HTTP 500 Internal Server Error
    }
}
