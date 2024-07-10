package Journey_of_Taro_V3.Journey_of_Taro_V3.models;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ImageTest {

    @InjectMocks
    private Image image;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testImageCreation() throws IOException {
        String originalFilename = "testImage.jpg";
        String contentType = "image/jpeg";
        byte[] content = "Test Image Content".getBytes();
        String imageUrl = "http://localhost:8080/uploads/images/testImage.jpg";
        String imageName = "Test Image";
        String imageAltName = "Test Image Alt";

        CustomMultipartFile customMultipartFile = new CustomMultipartFile(originalFilename, contentType, content);

        // Create the Image instance using the byte array from CustomMultipartFile
        image = new Image(customMultipartFile.getBytes(), imageUrl, imageName, imageAltName, customMultipartFile.getOriginalFilename(), customMultipartFile.getSize());

        assertNotNull(image);
        assertEquals(originalFilename, image.getFileName());
        assertEquals(content.length, image.getFileSize());
        assertEquals(imageUrl, image.getImageUrl());
        assertEquals(imageName, image.getImageName());
        assertEquals(imageAltName, image.getImageAltName());
    }
}
