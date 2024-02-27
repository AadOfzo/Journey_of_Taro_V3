package Journey_of_Taro_V3.Journey_of_Taro_V3.services.song;

import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music.SongRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.music.SongServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SongDeleteServiceImpl implements SongDeleteService {

    private static final Logger logger = LoggerFactory.getLogger(SongServiceImpl.class);
    private final SongRepository songRepository;

    public SongDeleteServiceImpl(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public void deleteSong(Long id) {
        songRepository.deleteById(id);
    }
}
