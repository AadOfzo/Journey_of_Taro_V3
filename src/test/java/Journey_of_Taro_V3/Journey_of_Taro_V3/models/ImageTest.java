package Journey_of_Taro_V3.Journey_of_Taro_V3.models;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.Image;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.SongCollection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ImageTest {

    @InjectMocks
    private Image image;

    @Mock
    private CustomMultipartFile customMultipartFile;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);

        // Arrange mock CustomMultipartFile
        String originalFilename = "testImage.jpg";
        byte[] content = "Test Image Content".getBytes();
        long fileSize = content.length;

        // Arrange image mock
        when(image.getImageData()).thenReturn(new byte[] { 0x00, 0x01 });
        when(customMultipartFile.getBytes()).thenReturn(content);
        when(customMultipartFile.getOriginalFilename()).thenReturn(originalFilename);
        when(customMultipartFile.getSize()).thenReturn(fileSize);
    }

    @Test
    void defaultConstructor_init() {
        image = new Image();

        // Assert
        assertNull(image.getId());
        assertNotNull(image.getImageData());
        assertNull(image.getImageName());
        assertNull(image.getImageAltName());
        assertNull(image.getFileName());
        assertNull(image.getFileSize());
        assertNull(image.getUploadTime());
        assertNull(image.getImageUrl());
        assertNull(image.getSongCollection());
    }

    @Test
    void byteArray_SetFieldsCorrectly() throws IOException {
        // Arrange
        String originalFilename = "testImage.jpg";
        byte[] content = "Test Image Content".getBytes();
        String imageUrl = "http://localhost:8080/uploads/images/testImage.jpg";
        String imageName = "Test Image";
        String imageAltName = "Test Image Alt";

        // Act
        image = new Image(content, imageUrl, imageName, imageAltName, originalFilename, (long) content.length);

        // Assert
        assertEquals(originalFilename, image.getFileName());      // Check filename
        assertEquals(content.length, image.getFileSize());       // Check file size
        assertEquals(imageUrl, image.getImageUrl());             // Check image URL
        assertEquals(imageName, image.getImageName());           // Check image name
        assertEquals(imageAltName, image.getImageAltName());     // Check image alt name
        assertNotNull(image.getUploadTime());                   // Ensure upload time is not null
    }

    @Test
    void customMultipartFile_SetFieldsCorrectly() throws IOException {
        // Arrange
        String imageUrl = "http://localhost:8080/uploads/images/testImage.jpg";
        String imageName = "Test Image";
        String imageAltName = "Test Image Alt";

        // Act
        image = new Image(customMultipartFile, imageUrl, customMultipartFile.getBytes(), imageName, imageAltName);

        // Assert
        assertNotNull(image);
        assertEquals("testImage.jpg", image.getFileName());
        assertEquals(15, image.getFileSize());
        assertEquals("http://localhost:8080/uploads/images/testImage.jpg", image.getImageUrl());
        assertEquals("Test Image", image.getImageName());
        assertEquals("Test Image Alt", image.getImageAltName());
        assertNotNull(image.getUploadTime());
    }

    @Test
    void gettersAndSetters_GetAndSetFieldsCorrectly() {
        // Arrange
        Long id = 1L;
        byte[] imageData = "Test Image Data".getBytes();
        String imageName = "Test Image";
        String imageAltName = "Test Image Alt";
        String fileName = "testImage.jpg";
        Long fileSize = 1024L;
        LocalDateTime uploadTime = LocalDateTime.now();
        String imageUrl = "http://localhost:8080/uploads/images/testImage.jpg";
        SongCollection songCollection = new SongCollection();

        // Act
        image.setId(id);
        image.setImageData(imageData);
        image.setImageName(imageName);
        image.setImageAltName(imageAltName);
        image.setFileName(fileName);
        image.setFileSize(fileSize);
        image.setUploadTime(uploadTime);
        image.setImageUrl(imageUrl);
        image.setSongCollection(songCollection);

        // Assert
        assertEquals(id, image.getId());
        assertArrayEquals(imageData, image.getImageData());
        assertEquals(imageName, image.getImageName());
        assertEquals(imageAltName, image.getImageAltName());
        assertEquals(fileName, image.getFileName());
        assertEquals(fileSize, image.getFileSize());
        assertEquals(uploadTime, image.getUploadTime());
        assertEquals(imageUrl, image.getImageUrl());
        assertEquals(songCollection, image.getSongCollection());
    }
}
