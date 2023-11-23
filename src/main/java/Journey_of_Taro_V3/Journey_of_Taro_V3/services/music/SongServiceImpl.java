package Journey_of_Taro_V3.Journey_of_Taro_V3.services.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;

    @Autowired
    public SongServiceImpl(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public List<SongDto> getAllSongs() {
        return null;
    }

    @Override
    public SongDto getSongById(Long id) {
        // Implement the logic to fetch a song by ID
        return null; // Placeholder, replace with actual implementation
    }

    @Override
    public SongDto addSong(SongInputDto dto) {
        return null;
    }

    @Override
    public void deleteSong(Long id) {

    }

    @Override
    public SongDto updateSong(Long id, SongInputDto inputDto) {
        return null;
    }
}
