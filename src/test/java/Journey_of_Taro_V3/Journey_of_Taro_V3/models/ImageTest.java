package Journey_of_Taro_V3.Journey_of_Taro_V3.models;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.Image;
import ch.qos.logback.core.net.SyslogOutputStream;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(SpringRunner.class)
@WebMvcTest(ImageTest.class)
public class ImageTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:3000/images"))
                .andExpect(status().isOk());
    }

    @Test
    public void testImageAttributes() {
        // Creating a test image file
        byte[] imageData = new byte[1024]; // Dummy image data
        String imageName = "Test Image";
        String imageAltName = "Alternate Test Image";
        String imageUrl = "localhost:8080/images/TestImage";
        CustomMultipartFile imageFile = new CustomMultipartFile("test_image.jpg", "image/jpeg", imageData);

        System.out.println("Image file created");

        // Creating a test image entity
        Image image = new Image(imageName, imageAltName, imageFile, imageUrl);

        // Printing attributes of the image entity
        System.out.println("Image Name: " + image.getImageName());
        System.out.println("Alternate Image Name: " + image.getImageAltName());
        System.out.println("Image URL: " + image.getImageUrl());
        System.out.println("File Name: " + image.getFileName());
        System.out.println("File Size: " + image.getFileSize());
        System.out.println("Upload Time: " + image.getUploadTime());
    }


}
