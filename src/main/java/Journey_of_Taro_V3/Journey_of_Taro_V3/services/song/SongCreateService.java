package Journey_of_Taro_V3.Journey_of_Taro_V3.services.song;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongInputDto;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public interface SongCreateService {
    SongDto create(SongInputDto inputDto) throws IOException;

}
