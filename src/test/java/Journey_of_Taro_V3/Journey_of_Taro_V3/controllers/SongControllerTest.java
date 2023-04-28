package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.SongInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.SongService;

@ExtendWith(MockitoExtension.class)
public class SongControllerTest {

    @Mock
    private SongService songService;

    @InjectMocks
    private SongController songController;

    private SongDto songDto;
    private SongInputDto songInputDto;
    private List<SongDto> songDtos;

    @BeforeEach
    void setUp() {
        songDto = new SongDto(1L, "Song Title", "Artist Name", true);
        songInputDto = new SongInputDto("Song Title", "Artist Name", true);
        songDtos = new ArrayList<>();
        songDtos.add(songDto);
    }

    @Test
    void getAllSongs_ReturnsListOfSongs() {
        when(songService.getAllSongs()).thenReturn(songDtos);
        ResponseEntity<List<SongDto>> response = songController.getAllSongs(Optional.empty());
        assert(response.getStatusCode() == HttpStatus.OK);
        assert(response.getBody().size() == 1);
        assert(response.getBody().get(0).equals(songDto));
    }

    @Test
    public void testGetAllSongsBySongTitle() {
        // arrange
        String songtitle = "TestSong";
        SongDto songDto = new SongDto(1L, songtitle, "TestArtist", true);
        List<SongDto> songDtos = Collections.singletonList(songDto);
        when(songService.getAllSongsBySongTitle(eq(songtitle))).thenReturn(songDtos);

        // act
        ResponseEntity<List<SongDto>> response = songController.getAllSongs(Optional.of(songtitle));

        // assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(songDtos, response.getBody());
    }


    @Test
    void getSong_ReturnsSongById() {
        when(songService.getSongById(1L)).thenReturn(songDto);
        ResponseEntity<SongDto> response = songController.getSong(1L);
        assert(response.getStatusCode() == HttpStatus.OK);
        assert(response.getBody().equals(songDto));
    }

    @Test
    void addSong_ReturnsNewSong() {
        when(songService.addSong(songInputDto)).thenReturn(songDto);
        ResponseEntity<Object> response = songController.addSong(songInputDto);
        assert(response.getStatusCode() == HttpStatus.CREATED);
        assert(response.getBody().equals(songDto));
    }

    @Test
    void deleteSong_ReturnsNoContent() {
        ResponseEntity<Object> response = songController.deleteSong(1L);
        assert(response.getStatusCode() == HttpStatus.NO_CONTENT);
    }

    @Test
    void updateSong_ReturnsUpdatedSong() {
        when(songService.updateSong(1L, songInputDto)).thenReturn(songDto);
        ResponseEntity<Object> response = songController.updateSong(1L, songInputDto);
        assert(response.getStatusCode() == HttpStatus.OK);
        assert(response.getBody().equals(songDto));
    }

}
