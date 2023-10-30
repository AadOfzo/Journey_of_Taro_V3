package Journey_of_Taro_V3.Journey_of_Taro_V3.services.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.SongCollectionType;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music.SongCollectionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SongCollectionTypeService {

    @Autowired
    private SongCollectionTypeRepository songCollectionTypeRepository;

    public List<SongCollectionType> getAllSongCollectionTypes() {
        return songCollectionTypeRepository.findAll();
    }

    public SongCollectionType getSongCollectionTypeById(Long id) {
        return songCollectionTypeRepository.findById(id).orElse(null);
    }

    public void saveSongCollectionType(SongCollectionType songCollectionType) {
        songCollectionTypeRepository.save(songCollectionType);
    }

    public void deleteSongCollectionTypeById(Long id) {
        songCollectionTypeRepository.deleteById(id);
    }
}
