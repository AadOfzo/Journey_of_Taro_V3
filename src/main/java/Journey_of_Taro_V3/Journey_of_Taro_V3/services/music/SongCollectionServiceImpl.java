package Journey_of_Taro_V3.Journey_of_Taro_V3.services.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongCollectionDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.RecordNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.Image;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.Song;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.SongCollection;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music.SongCollectionRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongCollectionServiceImpl implements SongCollectionService {

    private final SongCollectionRepository collectionRepository;
    private final SongRepository songRepository;

    @Autowired
    public SongCollectionServiceImpl(SongCollectionRepository collectionRepository, SongRepository songRepository) {
        this.collectionRepository = collectionRepository;
        this.songRepository = songRepository;
    }

    @Override
    public List<SongCollectionDto> getAllCollections() {
        List<SongCollection> collections = collectionRepository.findAll();
        return transferCollectionListToDtoList(collections);
    }
    @Override
    public SongCollectionDto getCollectionById(Long id) {
        SongCollection collection = collectionRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No collection found with the ID: " + id));
        return transferToCollectionDto(collection);
    }

    @Override
    public SongCollectionDto addCollection(SongCollectionDto dto) {
        SongCollection collection = transferToCollection(dto);
        collectionRepository.save(collection);
        return transferToCollectionDto(collection);
    }

    @Override
    public void deleteCollection(Long id) {
        collectionRepository.deleteById(id);
    }

    @Override
    public SongCollectionDto updateCollection(Long id, SongCollectionDto dto) {
        SongCollection collection = collectionRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No collection found with the ID: " + id));

        SongCollection updatedCollection = transferToCollection(dto);
        updatedCollection.setId(id);

        collectionRepository.save(updatedCollection);

        return transferToCollectionDto(updatedCollection);
    }

    @Override
    public void addSongToCollection(Long collectionId, Long songId) {
        SongCollection collection = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new RecordNotFoundException("No collection found with the ID: " + collectionId));

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new RecordNotFoundException("No song found with the ID: " + songId));

        collection.getSongs().add(song);
        collectionRepository.save(collection);
    }

    @Override
    public void addImageToCollection(Long collectionId, Image image) {
        SongCollection collection = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new RecordNotFoundException("No collection found with the ID: " + collectionId));

        collection.setCollectionImage(image);
        collectionRepository.save(collection);
    }

    private SongCollection transferToCollection(SongCollectionDto dto) {
        SongCollection collection = new SongCollection();

        collection.setSongCollectionName(dto.getSongCollectionName());
        collection.setSongCollectionType(dto.getSongCollectionType());

        // You can set other fields as needed

        return collection;
    }

    private SongCollectionDto transferToCollectionDto(SongCollection collection) {
        SongCollectionDto dto = new SongCollectionDto();

        dto.setId(collection.getId());
        dto.setSongCollectionName(collection.getSongCollectionName());

        // Check if the songs list is not null before calling stream()
        if (collection.getSongs() != null) {
            dto.setSongIds(collection.getSongs().stream().map(Song::getId).collect(Collectors.toList()));
        }

        dto.setCollectionImage(collection.getCollectionImage());
        dto.setSongCollectionType(collection.getSongCollectionType());

        // You can map other fields as needed

        return dto;
    }

    private List<SongCollectionDto> transferCollectionListToDtoList(List<SongCollection> collections) {
        return collections.stream().map(this::transferToCollectionDto).collect(Collectors.toList());
    }
}
