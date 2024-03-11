package Journey_of_Taro_V3.Journey_of_Taro_V3.services.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongCollectionDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongCollectionInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.RecordNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.Image;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.Song;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.SongCollection;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music.SongCollectionRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music.SongRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.images.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SongCollectionServiceImpl implements SongCollectionService {
//
//    private final SongCollectionRepository collectionRepository;
//    private final SongRepository songRepository;
//    private final SongService songService;
//    private final ImageService imageService;
//    private final SongCollectionService songCollectionService; // Add this line
//
//    @Autowired
//    public SongCollectionServiceImpl(SongCollectionRepository collectionRepository, SongRepository songRepository, SongService songService, ImageService imageService, SongCollectionService songCollectionService) {
//        this.collectionRepository = collectionRepository;
//        this.songRepository = songRepository;
//        this.songService = songService;
//        this.imageService = imageService;
//        this.songCollectionService = songCollectionService;
//    }
//
//    @Override
//    public List<SongCollectionDto> getAllSongCollections() {
//        return collectionRepository.findAll().stream()
//                .map(this::convertToDto)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public SongCollectionDto getSongCollectionById(Long id) {
//        SongCollection collection = collectionRepository.findById(id)
//                .orElseThrow(() -> new RecordNotFoundException("No collection found with the ID: " + id));
//        return convertToDto(collection);
//    }
//
////    @Override
////    public List<SongDto> getSongsByCollection(Long collectionId) {
////        // Retrieve the song collection by its ID
////        SongCollection collection = collectionRepository.findById(collectionId)
////                .orElseThrow(() -> new RecordNotFoundException("No collection found with the ID: " + collectionId));
////
////        // Retrieve the songs from the collection
////        List<Song> songs = collection.getSongs();
////
////        // Convert songs to DTOs and return
////        return transferSongListToDtoList(songs);
////    }
//
//    @Override
//    public SongCollectionDto createSongCollection(SongCollectionInputDto songCollectionInputDto) {
//        SongCollection songCollection = new SongCollection(songCollectionInputDto.getSongIds());
//        collectionRepository.save(songCollection);
//        SongCollectionDto songCollectionDto = new SongCollectionDto(songCollection.getId(), songCollection.getSongIds());
//        return songCollectionDto;
//    }
//
//
////    @Override
////    public void deleteSongCollection(Long id) {
////        collectionRepository.deleteById(id);
////    }
//
//    @Override
//    public SongCollectionDto updateSongCollection(Long id, SongCollectionInputDto dto) {
//        SongCollection collection = collectionRepository.findById(id)
//                .orElseThrow(() -> new RecordNotFoundException("No collection found with the ID: " + id));
//        // Update collection with new DTO values
//        collectionRepository.save(collection);
//        return convertToDto(collection);
//    }
//
//
////    @Override
////    public void addSongsToCollection(Long collectionId, List<Long> songIds) {
////        SongCollection collection = collectionRepository.findById(collectionId)
////                .orElseThrow(() -> new RecordNotFoundException("No collection found with the ID: " + collectionId));
////        List<Song> songsToAdd = songRepository.findAllById(songIds);
////        collection.addSongsToCollection(songsToAdd);
////        collectionRepository.save(collection);
////    }
//
//    @Override
//    public void addImageToSongCollection(Long collectionId, Image id) {
//        // Logic to add image to song collection
//    }
//
////    @Override
////    public void removeSongsFromCollection(Long collectionId, List<Long> songIds) {
////        // Retrieve the song collection by its ID
////        SongCollection collection = collectionRepository.findById(collectionId)
////                .orElseThrow(() -> new RecordNotFoundException("No collection found with the ID: " + collectionId));
////
////        // Retrieve the songs by their IDs
////        List<Song> songsToRemove = songRepository.findAllById(songIds);
////
////        // Remove the songs from the collection
////        collection.getSongs().removeAll(songsToRemove);
////    }
//
//    private SongCollectionDto convertToDto(SongCollection collection) {
//        SongCollectionDto dto = new SongCollectionDto();
//        // Map fields from collection to dto
//        return dto;
//    }
//
//    private List<SongDto> transferSongListToDtoList(List<Song> songs) {
//        List<SongDto> songDtoList = new ArrayList<>();
//        for (Song song : songs) {
//            SongDto songDto = new SongDto();
//            // Convert song to songDto (you can define this logic as needed)
//            // Example: songDto.setId(song.getId());
//            songDtoList.add(songDto);
//        }
//        return songDtoList;
//    }
//
//    private SongCollection convertToEntity(SongCollectionInputDto dto) {
//        SongCollection collection = new SongCollection(dto.getSongIds());
//        // Map fields from dto to collection
//        return collection;
//    }

}
