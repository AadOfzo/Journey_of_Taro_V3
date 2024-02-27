package Journey_of_Taro_V3.Journey_of_Taro_V3.services.song;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.RecordNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.Song;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music.SongRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SongUpdateServiceImpl implements SongUpdateService {

    private final SongRepository songRepository;

    public SongUpdateServiceImpl(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public SongDto updateSong(Long id, SongInputDto inputDto) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No song found with ID: " + id));

        // Update song attributes based on the input DTO
        song.setSongTitle(inputDto.getSongTitle());
        // Update other attributes as needed...

        // Save the updated song
        Song updatedSong = songRepository.save(song);

        return transferToSongDto(updatedSong);
    }

    SongDto transferToSongDto(Song song) {
        SongDto dto = new SongDto();

        dto.setId(song.getId());
        dto.setSongTitle(song.getSongTitle());

        return dto;
    }
}
