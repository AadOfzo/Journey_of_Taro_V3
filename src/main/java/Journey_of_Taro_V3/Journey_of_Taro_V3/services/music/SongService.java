package Journey_of_Taro_V3.Journey_of_Taro_V3.services.music;


import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongInputDto;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface SongService {

    SongDto getSong(Long id);
    List<SongDto> getAllSongs();

    SongDto getSongById(Long id);

    SongDto addSong(SongInputDto inputDto) throws IOException;

    SongDto saveSong(SongInputDto inputDto);

    SongDto updateSong(Long id, SongInputDto newSong);

    void deleteSong(Long id);


}