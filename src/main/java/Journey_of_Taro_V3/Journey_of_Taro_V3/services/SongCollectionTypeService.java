package Journey_of_Taro_V3.Journey_of_Taro_V3.services;

import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.SongCollectionTypeRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.SongRepository;
import org.springframework.stereotype.Service;

@Service
public class SongCollectionTypeService {

    private SongCollectionTypeRepository songCollectionTypeRepository;

    private final SongRepository songRepository;
    private final SongService songService;

    public SongCollectionTypeService(SongCollectionTypeRepository songCollectionTypeRepository, SongRepository songRepository, SongService songService) {
        this.songCollectionTypeRepository = songCollectionTypeRepository;
        this.songRepository = songRepository;
        this.songService = songService;
    }

}
