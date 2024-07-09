package Journey_of_Taro_V3.Journey_of_Taro_V3.models;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.Image;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ImageTest {

    @Test
    public void testImageAttributes() throws IOException {
        // Creating a test image file
        byte[] imageData = new byte[1024]; // Dummy image data
        String imageName = "Test Image";
        String imageAltName = "Alternate Test Image";
        String imageUrl = "localhost:8080/images/TestImage";
        CustomMultipartFile imageFile = new CustomMultipartFile("test_image.jpg", "image/jpeg", imageData);

        // Creating a test image entity
        Image image = new Image(imageFile, imageUrl, imageName, imageAltName);

        // Assertions to verify the attributes of the image entity
        assertEquals(imageName, image.getImageName());
        assertEquals(imageAltName, image.getImageAltName());
        assertEquals(imageUrl, image.getImageUrl());
        assertEquals("test_image.jpg", image.getFileName());
        assertEquals(imageData.length, image.getFileSize());
        assertNotNull(image.getUploadTime());
        assertArrayEquals(imageData, image.getImageData());
    }
}
