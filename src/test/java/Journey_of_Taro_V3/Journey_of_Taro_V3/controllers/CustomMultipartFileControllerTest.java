package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.images.ImageRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music.SongRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.users.UserRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.images.ImageService;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.music.SongService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomMultipartFileController.class)
@ContextConfiguration(classes = {CustomMultipartFileControllerTest.TestConfig.class})
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
    public void testFileUploadController_IOException() throws Exception {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("file", "testFile.txt", "text/plain", "test data".getBytes());

        // Mock the environment to throw IOException when getProperty is called
        Mockito.when(environment.getProperty(eq("base.url"), eq("http://localhost:8080"))).thenThrow(new IOException("IO Exception"));

        // Act & Assert
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/fileUpload")
                        .file(file))
                .andExpect(status().isInternalServerError())
                .andReturn();

        // Assert
        MockHttpServletResponse response = result.getResponse();
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());

        // Optionally, assert the content of the response if needed
        String content = response.getContentAsString();
        assertEquals("Error occurred while processing file upload", content);
    }


    @Test
    public void testFileUploadController_Audio() throws Exception {
        // Arrange
        String fileName = "testAudio.mp3";
        byte[] fileContent = "Test audio content".getBytes();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", fileName, "audio/mpeg", fileContent);

        SongDto songDto = new SongDto();
        when(songService.addSong(any(SongInputDto.class))).thenReturn(songDto);

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
        assertEquals(songDto, responseEntity.getBody());
    }

    @Test
    public void testFileUploadController_Image() throws Exception {
        // Arrange
        String fileName = "testImage.png";
        byte[] fileContent = "Test image content".getBytes();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", fileName, "image/png", fileContent);

        ImageDto imageDto = new ImageDto();
        when(imageService.addImage(any(ImageInputDto.class))).thenReturn(imageDto);

        // Act
        var result = mockMvc.perform(MockMvcRequestBuilders.multipart("/fileUpload")
                        .file(mockMultipartFile))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        ResponseEntity<?> responseEntity = (ResponseEntity<?>) result.getAsyncResult();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(imageDto, responseEntity.getBody());
    }

    @Test
    public void testFileUploadController_UnsupportedFileType() throws Exception {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("file", "testFile.xyz", "application/xyz", "test data".getBytes());

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.multipart("/fileUpload")
                        .file(file))
                .andExpect(status().isInternalServerError()); // Assert HTTP 500 Internal Server Error
    }

    @Test
    public void testFileUploadController_EmptyFile() throws Exception {
        // Arrange
        MockMultipartFile emptyFile = new MockMultipartFile("file", "empty.txt", "text/plain", new byte[0]);

        // Act & Assert
        var result = mockMvc.perform(MockMvcRequestBuilders.multipart("/fileUpload")
                        .file(emptyFile))
                .andExpect(status().isInternalServerError())
                .andReturn();

        // Assert
        ResponseEntity<?> responseEntity = (ResponseEntity<?>) result.getAsyncResult();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("File is empty", responseEntity.getBody());
    }

    @Configuration
    static class TestConfig {
        @Bean
        public Environment environment() {
            StandardEnvironment environment = new StandardEnvironment();
            environment.addActiveProfile("test");
            return environment;
        }
    }
}
