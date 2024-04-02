package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers;

import Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.images.ImageController;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.images.ImageService;
import Journey_of_Taro_V3.Journey_of_Taro_V3.utils.JwtUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Arrays;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ImageController.class)
@AutoConfigureMockMvc(addFilters = false) // Exclude security filters
public class ImageControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImageService imageService;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    public void testGetAllImages() throws Exception {
        List<ImageDto> mockImageList = Arrays.asList(
                new ImageDto(1L, "image1.jpg", "Alt 1", "http://localhost:8080/image1.jpg"),
                new ImageDto(2L, "image2.jpg", "Alt 2", "http://localhost:8080/image2.jpg")
        );

        Mockito.when(imageService.getAllImages()).thenReturn(mockImageList);

        mockMvc.perform(get("/images"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) jsonPath("$", hasSize(2)))
                .andExpect((ResultMatcher) jsonPath("$[0].imageName", is("image1.jpg")))
                .andExpect((ResultMatcher) jsonPath("$[1].imageName", is("image2.jpg")));
    }
}

