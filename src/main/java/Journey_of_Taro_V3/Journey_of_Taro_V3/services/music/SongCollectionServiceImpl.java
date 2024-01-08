package Journey_of_Taro_V3.Journey_of_Taro_V3.services.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongCollectionDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongCollectionInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.RecordNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.Image;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.Song;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.SongCollection;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music.SongCollectionRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<SongCollectionDto> getAllSongCollections() {
        List<SongCollection> collections = collectionRepository.findAll();
        return transferCollectionListToDtoList(collections);
    }

    @Override
    public SongCollectionDto getSongCollectionById(Long id) {
        SongCollection collection = collectionRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No collection found with the ID: " + id));
        return transferToSongCollectionDto(collection);
    }

    @Override
    public SongCollectionDto createSongCollection(SongCollectionInputDto dto) {
        SongCollection collection = transferToSongCollection(dto);
        collectionRepository.save(collection);
        return transferToSongCollectionDto(collection);
    }

    @Override
    public void deleteSongCollection(Long id) {
        collectionRepository.deleteById(id);
    }

    @Override
    public SongCollectionDto updateSongCollection(Long id, SongCollectionInputDto dto) {
        SongCollection collection = collectionRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No collection found with the ID: " + id));

        SongCollection updatedCollection = transferToSongCollection(dto);
        updatedCollection.setId(id);

        collectionRepository.save(updatedCollection);

        return transferToSongCollectionDto(updatedCollection);
    }

    @Override
    public void addImageToSongCollection(Long collectionId, Image image) {
        SongCollection collection = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new RecordNotFoundException("No collection found with the ID: " + collectionId));

        collection.setImage(image);
        collectionRepository.save(collection);
    }

    private SongCollectionDto transferToSongCollectionDto(SongCollection collection) {
        SongCollectionDto dto = new SongCollectionDto();

        dto.setId(collection.getId());
        dto.setSongCollectionTitle(collection.getSongCollectionTitle());
        // You can set other fields as needed

        return dto;
    }

    private List<SongCollectionDto> transferCollectionListToDtoList(List<SongCollection> collections) {
        return collections.stream().map(this::transferToSongCollectionDto).collect(Collectors.toList());
    }

    private SongCollection transferToSongCollection(SongCollectionInputDto dto) {
        // Assuming you have a constructor in SongCollection that takes List<Song>, String, and Image as parameters
        List<SongDto> songDtos = dto.getSongs();
        List<Song> songs = convertToSongs(songDtos);

        ImageDto imageDto = dto.getImage();
        Image image = convertToImage(imageDto);

        // Assuming you have a constructor in SongCollection that takes List<Song>, String, and Image as parameters
        return new SongCollection(songs, dto.getSongCollectionTitle(), image);
    }


    // Additional methods for conversion
    private List<Song> convertToSongs(List<SongDto> songDtos) {
        // Implement logic to convert a list of SongDto to a list of Song
        // For example, iterate through songDtos and convert each to a Song
        // Return the list of songs
        return new ArrayList<>();  // Placeholder, replace with actual logic
    }

    private Image convertToImage(ImageDto imageDto) {
        // Implement logic to convert an ImageDto to an Image
        // Return the image
        return new Image();  // Placeholder, replace with actual logic
    }

}
