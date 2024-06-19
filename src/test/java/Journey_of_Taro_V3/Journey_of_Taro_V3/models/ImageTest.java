package Journey_of_Taro_V3.Journey_of_Taro_V3.models;

import Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.images.ImageController;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.CustomMultipartFile;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.Image;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.images.ImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@WebMvcTest(ImageController.class)
@AutoConfigureMockMvc
public class ImageTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImageService imageService; // Assuming ImageService is used in ImageController

    @BeforeEach
    void setUp() {
        // Initialize any necessary mocks or configurations
    }

    @Test
    void testImageEndpoint() throws Exception {
        // Mocking CustomMultipartFile for image file
        byte[] imageData = new byte[1024]; // Dummy image data
        CustomMultipartFile imageFile = new CustomMultipartFile("test_image.jpg", "image/jpeg", imageData);

        // Mocking behavior of ImageService to return a DTO
        ImageDto mockImageDto = new ImageDto();
        mockImageDto.setImageName("Test Image");
        mockImageDto.setImageAltName("Alternate Test Image");
        mockImageDto.setImageUrl("http://localhost:8080/images/TestImage");


        when(imageService.addImage(any(ImageInputDto.class))).thenReturn(mockImageDto);

        // Testing POST endpoint for adding image
        mockMvc.perform(multipart("/images")
                        .file("file", imageFile.getBytes())
                        .param("imageName", "Test Image")
                        .param("imageAltName", "Alternate Test Image"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost:8080/images/TestImage"))
                .andExpect(content().string("http://localhost:8080/images/TestImage"));

        // Testing GET endpoint for retrieving all images
        mockMvc.perform(MockMvcRequestBuilders.get("/images"))
                .andExpect(status().isOk());

        // Testing GET endpoint for retrieving image by ID
        mockMvc.perform(MockMvcRequestBuilders.get("/images/{id}", 1L))
                .andExpect(status().isOk());

        // Testing DELETE endpoint for deleting image by ID
        mockMvc.perform(MockMvcRequestBuilders.delete("/images/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}
