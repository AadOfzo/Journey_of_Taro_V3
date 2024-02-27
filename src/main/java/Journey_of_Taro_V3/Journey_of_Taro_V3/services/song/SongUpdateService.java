package Journey_of_Taro_V3.Journey_of_Taro_V3.services.song;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongInputDto;
import org.springframework.stereotype.Service;


@Service
public interface SongUpdateService {
    SongDto updateSong(Long id, SongInputDto newSong);

}
