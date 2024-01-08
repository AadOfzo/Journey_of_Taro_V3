package Journey_of_Taro_V3.Journey_of_Taro_V3.models;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.Song;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.users.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SongTest {
    @Test
    public void testSongCreation() {
        // Maak User aan:
        User artist = new User();
        artist.setArtistName("Test Artist");

        // Maak new Song en set Artist
        Song song = new Song();
        song.setSongTitle("Test Song");
        song.setArtistName("Test Artist Name");

        assertEquals("Test Song", song.getSongTitle());
        assertEquals("Test Artist", song.getArtistName());
    }
}