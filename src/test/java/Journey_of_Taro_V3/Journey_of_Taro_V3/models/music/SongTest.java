package Journey_of_Taro_V3.Journey_of_Taro_V3.models.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.BadRequestException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.CustomMultipartFile;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.MimeType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SongTest {

    @Mock
    private CustomMultipartFile mockFile;

    @Mock
    private User mockUser;

    private Song song;

    @BeforeEach
    void setUp() {
        // Mocking behavior for CustomMultipartFile
        when(mockFile.isEmpty()).thenReturn(false);
    }

    @Test
    void testSongConstructor_ValidInputs_Success() {
        // Arrange
        song = new Song("Test Song", mockFile, mockUser, "http://example.com/song.mp3",
                SongCollectionType.Singles, MimeType.valueOf("audio/mpeg"));

        // Assert
        assertEquals("Test Song", song.getSongTitle());
        assertEquals(mockFile, song.getSongFile());
        assertEquals(mockUser, song.getArtistName());
        assertEquals("http://example.com/song.mp3", song.getSongUrl());
        assertEquals(SongCollectionType.Singles, song.getSongCollectionType());
    }

    @Test
    void testSongConstructor_NullTitle_ExceptionThrown() {
        // Act & Assert
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            new Song(null, mockFile, mockUser, "http://example.com/song.mp3",
                    SongCollectionType.Singles, MimeType.valueOf("audio/mpeg"));
        });

        assertEquals("Song title cannot be null or empty", exception.getMessage());
    }

    @Test
    void testSongConstructor_NullFile_ExceptionThrown() {
        // Act & Assert
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            new Song("Test Song", null, mockUser, "http://example.com/song.mp3",
                    SongCollectionType.Singles, MimeType.valueOf("audio/mpeg"));
        });

        assertEquals("Please choose an mp3 Audio file", exception.getMessage());
    }
}
