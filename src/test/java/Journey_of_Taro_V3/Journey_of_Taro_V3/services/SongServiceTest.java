package Journey_of_Taro_V3.Journey_of_Taro_V3.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.SongTitleNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.Song;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.SongRepository;

public class SongServiceTest {

    private SongRepository songRepository;
    private SongService songService;

    @Before
    public void setUp() {
        songRepository = mock(SongRepository.class);
        songService = new SongService(songRepository);
    }

    @Test
    public void testGetAllSongs() {
        // given
        List<Song> songs = new ArrayList<>();
        songs.add(new Song(1L, "song1", "artist1", true));
        songs.add(new Song(2L, "song2", "artist2", false));

        when(songRepository.findAll()).thenReturn(songs);
        // when
        List<SongDto> songDtos = songService.getAllSongs();
        // then
        assertEquals(2, songDtos.size());
        assertEquals("song1", songDtos.get(0).getSongtitle());
        assertEquals("artist2", songDtos.get(1).getArtistname());
    }

    @Test
    public void testGetAllSongsBySongTitle() {
        List<Song> songs = new ArrayList<>();
        songs.add(new Song(1L, "song1", "artist1", true));
        songs.add(new Song(2L, "song2", "artist2", false));

        when(songRepository.findAllSongsBySongtitleEqualsIgnoreCase("song1")).thenReturn(songs.subList(0, 1));

        List<SongDto> songDtos = songService.getAllSongsBySongTitle("song1");

        assertEquals(1, songDtos.size());
        assertEquals("song1", songDtos.get(0).getSongtitle());
        assertEquals("artist1", songDtos.get(0).getArtistname());
        assertEquals(true, songDtos.get(0).getIsfavorited());
    }

    @Test(expected = SongTitleNotFoundException.class)
    public void testGetSongByIdWithException() {
        when(songRepository.findById(1L)).thenReturn(Optional.empty());

        songService.getSongById(1L);
    }


    @Test
    public void testGetSongById() {
        Song song = new Song(1L, "song1", "artist1", true);

        when(songRepository.findById(1L)).thenReturn(java.util.Optional.of(song));

        SongDto songDto = songService.getSongById(1L);

        assertEquals("song1", songDto.getSongtitle());
        assertEquals("artist1", songDto.getArtistname());
        assertEquals(true, songDto.getIsfavorited());
    }

    // addSong, updateSong, deleteSong test cases can also be added similarly

}
