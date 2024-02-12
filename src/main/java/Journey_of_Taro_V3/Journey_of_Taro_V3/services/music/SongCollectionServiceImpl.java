package Journey_of_Taro_V3.Journey_of_Taro_V3.services.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongCollectionDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongCollectionInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.RecordNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.CustomMultipartFile;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.Image;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.Song;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.SongCollection;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music.SongCollectionRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SongCollectionServiceImpl implements SongCollectionService {

    private final SongCollectionRepository collectionRepository;
    private final SongRepository songRepository;

    @Autowired
    public SongCollectionServiceImpl(SongCollectionRepository collectionRepository, SongRepository songRepository) {
        this.collectionRepository = collectionRepository;
        this.songRepository = songRepository;
    }

    @Override
    public List<SongCollectionDto> getAllSongCollections() {
        return collectionRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public SongCollectionDto getSongCollectionById(Long id) {
        SongCollection collection = collectionRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No collection found with the ID: " + id));
        return convertToDto(collection);
    }

    @Override
    public SongCollectionDto createSongCollection(SongCollectionInputDto dto, MultipartFile imageFile, List<MultipartFile> songFiles) {
        SongCollection collection = convertToEntity(dto);
        // Process imageFile and songFiles here using CustomMultipartFile
        collectionRepository.save(collection);
        return convertToDto(collection);
    }

    @Override
    public void deleteSongCollection(Long id) {
        collectionRepository.deleteById(id);
    }

    @Override
    public SongCollectionDto updateSongCollection(Long id, SongCollectionInputDto dto) {
        SongCollection collection = collectionRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No collection found with the ID: " + id));
        // Update collection with new DTO values
        collectionRepository.save(collection);
        return convertToDto(collection);
    }

    @Override
    public void addImageToSongCollection(Long collectionId, Image image) {
        // Method for Collection Image.
    }

    private SongCollectionDto convertToDto(SongCollection collection) {
        SongCollectionDto dto = new SongCollectionDto();
        // Map fields from collection to dto
        return dto;
    }

    private SongCollection convertToEntity(SongCollectionInputDto dto) {
        SongCollection collection = new SongCollection();
        // Map fields from dto to collection
        return collection;
    }

}
