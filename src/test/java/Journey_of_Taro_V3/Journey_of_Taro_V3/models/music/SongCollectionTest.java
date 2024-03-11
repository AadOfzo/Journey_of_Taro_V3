package Journey_of_Taro_V3.Journey_of_Taro_V3.models.music;


import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.*;

import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music.SongCollectionRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music.SongRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.music.SongCollectionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class SongCollectionTest {
//
//    @Mock
//    private SongRepository songRepository;
//    @Mock
//    private SongCollectionRepository songCollectionRepository;
//    @InjectMocks
//    private SongCollectionServiceImpl songCollectionService;
//
//    @Test
//    public void testAddMultipleSongsToSongCollection() {
//        // Create mock songs
//        Song song1 = new Song();
//        Song song2 = new Song();
//        Song song3 = new Song();
//        Song song4 = new Song();
//
//        // Create a mock image
//        Image mockImage = new Image();
//
//        // Create a list of songs
//        List<Song> songs = new ArrayList<>();
//        songs.add(song1);
//        songs.add(song2);
//        songs.add(song3);
//        songs.add(song4);
//
//        // Create a song collection
//        SongCollection songCollection = new SongCollection();
//        songCollection.setId(1L); // Assuming ID is set for the song collection
//        songCollection.setSongs(new ArrayList<>()); // Ensure the songs list is initialized
//        songCollection.setSongCollectionTitle("My Collection");
//        songCollection.setImage(mockImage);
//
//        // Mock repository behavior
//        when(songCollectionRepository.findById(1L)).thenReturn(Optional.of(songCollection));
//
//        // Extract the IDs from the list of songs
//        List<Long> songIds = songs.stream()
//                .map(Song::getId)
//                .collect(Collectors.toList());
//
//    // Call the method to add songs to the collection using song IDs
//        songCollectionService.addSongsToCollection(1L, songIds);
//
//
//        // Verify that the songs are added to the collection
//        verify(songCollectionRepository, times(1)).findById(1L); // Ensure findById was called once
//        assertEquals(4, songCollection.getSongs().size());
//    }
//

}

