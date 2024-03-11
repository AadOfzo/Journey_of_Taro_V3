package Journey_of_Taro_V3.Journey_of_Taro_V3.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.RecordNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.Song;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music.SongRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.music.SongServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class SongServiceImplTest {

    @Mock
    private SongRepository songRepository;

    @InjectMocks
    private SongServiceImpl songService;

    @Test
    public void testGetSong() {
        // Create a sample song entity
        Song sampleSong = new Song();
        sampleSong.setId(1L);
        sampleSong.setSongTitle("Sample Song");

        // Mock the behavior of the song repository to return the sample song entity
        when(songRepository.findById(1L)).thenReturn(Optional.of(sampleSong));

        // Call the getSong method with the ID of the sample song
        SongDto songDto = songService.getSong(1L);

        // Verify that the returned SongDto has the expected attributes
        assertNotNull(songDto);
        assertEquals(sampleSong.getId(), songDto.getId());
        assertEquals(sampleSong.getSongTitle(), songDto.getSongTitle());
    }

    @Test
    public void testGetSongById() {
        // Create a sample song entity
        Song sampleSong = new Song();
        sampleSong.setId(1L);
        sampleSong.setSongTitle("Sample Song");

        // Mock the behavior of the song repository to return the sample song entity
        when(songRepository.findById(1L)).thenReturn(Optional.of(sampleSong));

        // Call the getSongById method with the ID of the sample song
        SongDto songDto = songService.getSongById(1L);

        // Verify that the returned SongDto has the expected attributes
        assertNotNull(songDto);
        assertEquals(sampleSong.getId(), songDto.getId());
        assertEquals(sampleSong.getSongTitle(), songDto.getSongTitle());
    }

    @Test
    public void testGetAllSongs() {
        // Create a sample song entity
        Song sampleSong = new Song();
        sampleSong.setId(1L);
        sampleSong.setSongTitle("Sample Song");

        // Mock the behavior of the song repository to return a list containing the sample song entity
        when(songRepository.findAll()).thenReturn(Collections.singletonList(sampleSong));

        // Call the getAllSongs method
        List<SongDto> songDtoList = songService.getAllSongs();

        // Verify that the returned list is not null and contains one SongDto
        assertNotNull(songDtoList);
        assertFalse(songDtoList.isEmpty());

        // Verify the attributes of the first SongDto in the list
        SongDto firstSongDto = songDtoList.get(0);
        assertEquals(sampleSong.getId(), firstSongDto.getId());
        assertEquals(sampleSong.getSongTitle(), firstSongDto.getSongTitle());
    }


//    @Test
//    public void testGetSong() {
//        // Create a sample song entity
//        Song sampleSong = new Song();
//        sampleSong.setId(1L);
//        sampleSong.setSongTitle("Sample Song");
//
//        // Mock the behavior of the song repository to return the sample song entity
//        when(songRepository.findById(1L)).thenReturn(Optional.of(sampleSong));
//
//        // Call the getSong method with the ID of the sample song
//        SongDto songDto = songService.getSong(1L);
//
//        // Verify that the returned SongDto has the expected attributes
//        assertNotNull(songDto);
//        assertEquals(sampleSong.getId(), songDto.getId());
//        assertEquals(sampleSong.getSongTitle(), songDto.getSongTitle());
//    }

    @Test
    public void testGetSongNotFound() {
        // Mock the behavior of the song repository to return an empty optional
        when(songRepository.findById(1L)).thenReturn(Optional.empty());

        // Call the getSong method with a non-existent ID
        assertThrows(RecordNotFoundException.class, () -> songService.getSong(1L));
    }


}
