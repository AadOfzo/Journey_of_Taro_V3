package Journey_of_Taro_V3.Journey_of_Taro_V3.services.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongServiceImpl {

    private final SongRepository songRepository;

    @Autowired
    public SongServiceImpl(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public List<SongDto> getAllSongs() {
        // Implement the logic to get all songs
        return null; // Placeholder, replace with actual implementation
    }

    public SongDto getSongById(Long id) {
        // Implement the logic to fetch a song by ID
        return null; // Placeholder, replace with actual implementation
    }

    public SongDto addSong(SongInputDto inputDto) {
        // Implement the logic to add a song
        return null; // Placeholder, replace with actual implementation
    }

    public SongDto updateSong(Long id, SongInputDto inputDto) {
        // Implement the logic to update a song by ID
        return null; // Placeholder, replace with actual implementation
    }

    public void deleteSong(Long id) {
        // Implement the logic to delete a song by ID
    }
}
