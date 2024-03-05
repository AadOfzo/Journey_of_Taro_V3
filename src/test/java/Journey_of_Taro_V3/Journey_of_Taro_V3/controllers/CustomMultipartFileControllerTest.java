package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.images.ImageService;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.music.SongService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomMultipartFileControllerTest {

    private ImageService imageService = mock(ImageService.class);
    private SongService songService = mock(SongService.class);
    private CustomMultipartFileController multipartFileController = new CustomMultipartFileController(imageService, songService);

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

    @Test
    void testFileUploadController_UnsupportedFileType() {
        // Arrange
        String fileName = "testFile.txt";
        byte[] fileContent = "Test file content".getBytes();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", fileName, "text/plain", fileContent);

        // Act
        // Assert
        IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () ->
                multipartFileController.fileUploadController(mockMultipartFile));
        assertEquals("Unsupported file type", exception.getMessage());
    }
}
