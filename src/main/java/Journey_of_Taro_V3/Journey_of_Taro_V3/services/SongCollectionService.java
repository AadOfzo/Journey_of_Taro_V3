package Journey_of_Taro_V3.Journey_of_Taro_V3.services;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.SongCollectionDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.SongCollectionNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.SongTitleNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.Song;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.SongCollection;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.SongCollectionType;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.SongCollectionRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.SongRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongCollectionService {
    private final SongCollectionRepository songCollectionRepository;
    private final SongRepository songRepository;

    public SongCollectionService(SongCollectionRepository songCollectionRepository, SongRepository songRepository) {
        this.songCollectionRepository = songCollectionRepository;
        this.songRepository = songRepository;
    }

    public SongCollection createSongCollection(SongCollectionDto songCollectionDto) {
        SongCollection songCollection = new SongCollection();
        songCollection.setName(songCollectionDto.getCollectionname());
        songCollection.setSongCollectionType(SongCollectionType.valueOf(songCollectionDto.getSongCollectionType().toUpperCase()));

        return songCollectionRepository.save(songCollection);
    }


    public SongCollection addSongToCollection(Long songCollectionId, Long songId) throws SongCollectionNotFoundException, SongTitleNotFoundException {
        SongCollection songCollection = songCollectionRepository.findById(songCollectionId)
                .orElseThrow(() -> new SongCollectionNotFoundException(songCollectionId));
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new SongTitleNotFoundException("Song not found with ID: " + songId));
        songCollection.getSongs().add(song);
        return songCollectionRepository.save(songCollection);
    }

    public List<SongCollection> getSongCollections() {
        return songCollectionRepository.findAll();
    }

    public SongCollection getSongCollectionById(Long id) throws SongCollectionNotFoundException {
        return songCollectionRepository.findById(id)
                .orElseThrow(() -> new SongCollectionNotFoundException(id));
    }

    public void deleteSongCollection(Long id) throws SongCollectionNotFoundException {
        SongCollection songCollection = songCollectionRepository.findById(id)
                .orElseThrow(() -> new SongCollectionNotFoundException(id));
        songCollectionRepository.delete(songCollection);
    }

    public SongCollection updateSongCollection(Long id, SongCollectionDto songCollectionDto) throws SongCollectionNotFoundException {
        SongCollection songCollection = songCollectionRepository.findById(id)
                .orElseThrow(() -> new SongCollectionNotFoundException(id));
        songCollection.setName(songCollectionDto.getName());
        songCollection.setSongCollectionType(SongCollectionType.valueOf(songCollectionDto.getSongCollectionType()));
        return songCollectionRepository.save(songCollection);
    }
}
