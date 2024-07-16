package Journey_of_Taro_V3.Journey_of_Taro_V3.services;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.RecordNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.Song;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.users.User;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music.SongRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.users.UserRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.music.SongServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SongServiceImplTest {

    @Mock
    private SongRepository songRepository;

    @Mock
    private UserRepository userRepository;

    private SongServiceImpl songService;

    private static final String SONGS_DIR = "songs";

    @BeforeEach
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);

        // Ensure the SONGS_DIR directory exists
        Path songsDirPath = Paths.get(SONGS_DIR);
        if (!Files.exists(songsDirPath)) {
            Files.createDirectory(songsDirPath);
        }

        songService = new SongServiceImpl(SONGS_DIR, songRepository, userRepository);
    }

    @Test
    public void testGetSong() {
        Song sampleSong = new Song();
        sampleSong.setId(1L);
        sampleSong.setSongTitle("Sample Song");

        when(songRepository.findById(1L)).thenReturn(Optional.of(sampleSong));

        SongDto songDto = songService.getSong(1L);

        assertNotNull(songDto);
        assertEquals(sampleSong.getId(), songDto.getId());
        assertEquals(sampleSong.getSongTitle(), songDto.getSongTitle());
    }

    @Test
    public void testGetSongById() {
        Song sampleSong = new Song();
        sampleSong.setId(1L);
        sampleSong.setSongTitle("Sample Song");

        when(songRepository.findById(1L)).thenReturn(Optional.of(sampleSong));

        SongDto songDto = songService.getSongById(1L);

        assertNotNull(songDto);
        assertEquals(sampleSong.getId(), songDto.getId());
        assertEquals(sampleSong.getSongTitle(), songDto.getSongTitle());
    }

    @Test
    public void testGetSongById_NotFound() {
        when(songRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RecordNotFoundException.class, () -> songService.getSongById(1L));
    }

    @Test
    public void testGetAllSongs() {
        Song sampleSong = new Song();
        sampleSong.setId(1L);
        sampleSong.setSongTitle("Sample Song");

        when(songRepository.findAll()).thenReturn(Collections.singletonList(sampleSong));

        List<SongDto> songDtoList = songService.getAllSongs();

        assertNotNull(songDtoList);
        assertFalse(songDtoList.isEmpty());

        SongDto firstSongDto = songDtoList.get(0);
        assertEquals(sampleSong.getId(), firstSongDto.getId());
        assertEquals(sampleSong.getSongTitle(), firstSongDto.getSongTitle());
    }

    @Test
    public void testAddSong() throws IOException {
        SongInputDto inputDto = new SongInputDto();
        inputDto.setSongTitle("New Song");
        MockMultipartFile mockFile = new MockMultipartFile("file", "song.mp3", "audio/mpeg", new byte[0]);
        inputDto.setSongFile(mockFile);
        inputDto.setArtistName("Artist");

        User user = new User();
        user.setUsername("Artist");

        when(userRepository.findByArtistName("Artist")).thenReturn(Optional.of(user));

        Song savedSong = new Song();
        savedSong.setId(1L);
        savedSong.setSongTitle("New Song");
        savedSong.setFileName("song.mp3");

        when(songRepository.save(any(Song.class))).thenReturn(savedSong);

        SongDto songDto = songService.addSong(inputDto);

        assertNotNull(songDto);
        assertEquals(savedSong.getId(), songDto.getId());
        assertEquals(savedSong.getSongTitle(), songDto.getSongTitle());
    }

    @Test
    public void testAddSong_UserNotFound() throws IOException {
        SongInputDto inputDto = new SongInputDto();
        inputDto.setArtistName("Unknown Artist");

        when(userRepository.findByArtistName("Unknown Artist")).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> songService.addSong(inputDto));
    }

    @Test
    public void testSaveSong() {
        SongInputDto inputDto = new SongInputDto();
        inputDto.setSongTitle("New Song");
        MockMultipartFile mockFile = new MockMultipartFile("file", "song.mp3", "audio/mpeg", new byte[0]);
        inputDto.setSongFile(mockFile);
        inputDto.setArtistName("Artist");

        User user = new User();
        user.setUsername("Artist");

        when(userRepository.findByArtistName("Artist")).thenReturn(Optional.of(user));

        Song savedSong = new Song();
        savedSong.setId(1L);
        savedSong.setSongTitle("New Song");

        when(songRepository.save(any(Song.class))).thenReturn(savedSong);

        SongDto songDto = songService.saveSong(inputDto);

        assertNotNull(songDto);
        assertEquals(savedSong.getId(), songDto.getId());
        assertEquals(savedSong.getSongTitle(), songDto.getSongTitle());
    }

    @Test
    public void testStoreSongFile() throws IOException {
        MockMultipartFile mockFile = new MockMultipartFile("file", "song.mp3", "audio/mpeg", new byte[0]);

        String songTitle = songService.storeSongFile(mockFile);

        assertEquals("song.mp3", songTitle);
        verify(songRepository, times(1)).save(any());
    }

    @Test
    public void testDownloadSongFile() {
        Resource resource = songService.downloadSongFile("song.mp3");
        assertNotNull(resource);
    }

    @Test
    public void testUpdateSong() {
        Song sampleSong = new Song();
        sampleSong.setId(1L);
        sampleSong.setSongTitle("Old Title");
        sampleSong.setFileName("song.mp3");

        SongInputDto inputDto = new SongInputDto();
        inputDto.setSongTitle("New Title");

        when(songRepository.findById(1L)).thenReturn(Optional.of(sampleSong));
        when(songRepository.save(any(Song.class))).thenReturn(sampleSong);

        SongDto updatedSongDto = songService.updateSong(1L, inputDto);

        assertNotNull(updatedSongDto);
        assertEquals("New Title", updatedSongDto.getSongTitle());
    }

    @Test
    public void testUpdateSong_NotFound() {
        when(songRepository.findById(1L)).thenReturn(Optional.empty());

        SongInputDto inputDto = new SongInputDto();
        inputDto.setSongTitle("New Title");

        assertThrows(RecordNotFoundException.class, () -> songService.updateSong(1L, inputDto));
    }

    @Test
    public void testDeleteSong() {
        songService.deleteSong(1L);
        verify(songRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetSongWithData() throws IOException {
        Path tempFile = Files.createTempFile("song", ".mp3");
        Files.write(tempFile, "test data".getBytes());

        String songTitle = tempFile.getFileName().toString();
        Song song = songService.getSongWithData(songTitle);

        assertNotNull(song);
        assertArrayEquals("test data".getBytes(), song.getSongData());

        Files.deleteIfExists(tempFile);
    }

    @Test
    public void testGetSongUrlByTitle() {
        Song sampleSong = new Song();
        sampleSong.setSongTitle("Sample Song");
        sampleSong.setSongUrl("songs/Sample Song");

        when(songRepository.findBySongTitle("Sample Song")).thenReturn(Optional.of(sampleSong));

        String songUrl = songService.getSongUrlByTitle("Sample Song");

        assertEquals("songs/Sample Song", songUrl);
    }

    @Test
    public void testGetSongUrlByTitle_NotFound() {
        when(songRepository.findBySongTitle("Unknown Song")).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> songService.getSongUrlByTitle("Unknown Song"));
    }
}
