package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers;

import Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.CustomMultipartFileController;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.Song;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music.SongRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.music.SongService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomMultipartFileControllerTest {

    @InjectMocks
    private CustomMultipartFileController controller;

    @Mock
    private SongService songService;

    @Mock
    private SongRepository songRepository; // Add this line to mock SongRepository

    @Test
    public void testFileUploadController() throws IOException {
        // Mock MultipartFile
        MockMultipartFile file = new MockMultipartFile("file", "test.mp3", "audio/mpeg", "test data".getBytes());

        // Mock the behavior of determineFileType method
        when(controller.determineFileType(anyString())).thenReturn("Audio");

        // Mock the behavior of songService.addSong method
        when(songService.addSong(any(SongInputDto.class))).thenReturn(new SongDto());

        // Call the method
        Object result = controller.fileUploadController(file);

        // Assert that the returned object is not null and is an instance of SongDto
        assertThat(result).isNotNull();
        assertThat(result).isInstanceOf(SongDto.class);

        // You can SongCreateService more assertions based on your requirements
    }

    @Test
    public void testAddSong() throws IOException {
        // Mock SongInputDto
        SongInputDto inputDto = new SongInputDto();
        inputDto.setSongTitle("Test Song");

        // Mock save method of songRepository
        when(songRepository.save(any(Song.class))).thenReturn(new Song()); // Mock the save method

        // Call the method and assert the returned SongDto
        SongDto result = songService.addSong(inputDto);
        assertThat(result).isNotNull();

        // You can SongCreateService more assertions based on your requirements
    }
}
