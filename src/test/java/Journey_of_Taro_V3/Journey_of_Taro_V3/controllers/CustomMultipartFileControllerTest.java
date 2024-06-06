package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.images.ImageRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music.SongRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.users.UserRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.images.ImageService;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.music.SongService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomMultipartFileController.class)
class CustomMultipartFileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ImageService imageService;

    @MockBean
    private ImageRepository imageRepository;

    @MockBean
    private SongService songService;

    @MockBean
    private SongRepository songRepository;

    @MockBean
    private Environment environment;

    private CustomMultipartFileController multipartFileController;

    @BeforeEach
    void setUp() {
        multipartFileController = new CustomMultipartFileController(imageService, songService, environment);
    }

    @Test
    public void testFileUploadController_UnsupportedFileType() throws Exception {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("file", "testFile.txt", "text/plain", "test data".getBytes());

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.multipart("/fileUpload")
                        .file(file))
                .andExpect(status().isInternalServerError()); // Assert HTTP 500 Internal Server Error
    }

    @Test
    public void testFileUploadController_Image() throws Exception {
        // Arrange
        String fileName = "testImage.png";
        byte[] fileContent = "Test image content".getBytes();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", fileName, "image/png", fileContent);

        ImageDto imageDto = new ImageDto();
        when(imageService.addImage(any())).thenReturn(imageDto);

        // Act
        var result = mockMvc.perform(MockMvcRequestBuilders.multipart("/fileUpload")
                        .file(mockMultipartFile)
                        .param("artistName", "testArtist")
                        .param("songTitle", "testTitle")
                        .param("userName", "testUser"))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        ResponseEntity<?> responseEntity = (ResponseEntity<?>) result.getAsyncResult();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(ImageDto.class, responseEntity.getBody().getClass());
    }

    @Test
    public void testFileUploadController_Audio() throws Exception {
        // Arrange
        String fileName = "testAudio.mp3";
        byte[] fileContent = "Test audio content".getBytes();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", fileName, "audio/mpeg", fileContent);

        SongDto songDto = new SongDto();
        when(songService.addSong(any())).thenReturn(songDto);

        // Act
        var result = mockMvc.perform(MockMvcRequestBuilders.multipart("/fileUpload")
                        .file(mockMultipartFile)
                        .param("artistName", "testArtist")
                        .param("songTitle", "testTitle")
                        .param("userName", "testUser"))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        ResponseEntity<?> responseEntity = (ResponseEntity<?>) result.getAsyncResult();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(SongDto.class, responseEntity.getBody().getClass());
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
}
