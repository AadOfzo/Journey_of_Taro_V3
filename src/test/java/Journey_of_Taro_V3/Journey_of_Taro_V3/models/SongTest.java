package Journey_of_Taro_V3.Journey_of_Taro_V3.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SongTest {
    @Test
    public void testSongCreation() {
        // Create a new Song
        Song song = new Song();
        song.setSongTitle("Test Song");
        song.setArtistName("Test Artist");
        song.setIsFavorited(true);

        // Check if properties are set correctly
        assertEquals("Test Song", song.getSongTitle());
        assertEquals("Test Artist", song.getArtistName());
        assertTrue(song.getIsFavorited());
    }
}