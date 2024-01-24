package Journey_of_Taro_V3.Journey_of_Taro_V3.models;

import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.BadRequestException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.Song;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.SongCollectionType;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.users.User;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SongTest {
//    @Test
//    public void testSongCreation() {
//        // Maak User aan:
//        User artist = new User();
//        artist.setArtistName("Test Artist");
//
//        // Maak new Song en set Artist
//        Song song = new Song();
//        song.setSongTitle("Test Song");
//        song.setArtistName("Test Artist");
//
//        assertEquals("Test Song", song.getSongTitle());
//        assertEquals("Test Artist", song.getArtistName());
//    }


    @Test
    public void testSongCreation() {
        // Mock MultipartFile
        MultipartFile songFile = mock(MultipartFile.class);
        when(songFile.isEmpty()).thenReturn(false);
        try {
            when(songFile.getBytes()).thenReturn(new byte[]{1, 2, 3}); // Provide sample bytes for song data
        } catch (Exception e) {
            throw new BadRequestException("Error getting bytes from MultipartFile");
        }

        // Mock SongCollectionType
        SongCollectionType collectionType = SongCollectionType.EPs;

        // Maak User aan:
        User artist = new User();
        artist.setArtistName("Test Artist");

        // Maak new Song en set Artist
        Song song = new Song("Test Song", songFile, "Test Artist", collectionType.name());

        assertEquals("Test Song", song.getSongTitle());
        assertEquals("Test Artist", song.getArtistName());
        assertEquals(collectionType, song.getSongCollectionType());
    }
}