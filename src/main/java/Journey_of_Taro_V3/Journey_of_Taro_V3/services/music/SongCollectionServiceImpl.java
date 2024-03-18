package Journey_of_Taro_V3.Journey_of_Taro_V3.services.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.*;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.RecordNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.Image;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.Song;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.SongCollection;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music.SongCollectionRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music.SongRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.images.ImageService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@Transactional
public class SongCollectionServiceImpl implements SongCollectionService {

    private final SongCollectionRepository collectionRepository;
    private final SongRepository songRepository;
    private final SongServiceImpl songService;
    private final ImageService imageService;

    @Autowired
    public SongCollectionServiceImpl(SongCollectionRepository collectionRepository, SongRepository songRepository, SongServiceImpl songService, ImageService imageService) {
        this.collectionRepository = collectionRepository;
        this.songRepository = songRepository;
        this.songService = songService;
        this.imageService = imageService;
    }


    @Override
    public void addSongsToCollection(Long collectionId, List<Long> songIds) {
        SongCollection collection = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new RecordNotFoundException("No collection found with the ID: " + collectionId));
        List<Song> songsToAdd = songRepository.findAllById(songIds);
        collection.addSongsToCollection(songsToAdd);
        collectionRepository.save(collection);
    }

    @Override
    public void removeSongsFromCollection(Long collectionId, List<Long> songIds) {

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
    public List<SongDto> getSongsByCollection(Long collectionId) {
        // Retrieve the song collection by its ID
        SongCollection collection = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new RecordNotFoundException("No collection found with the ID: " + collectionId));

        // Retrieve the songs from the collection
        List<Song> songs = collection.getSongs();

        // Convert songs to DTOs and return
        return songService.transferSongListToDtoList(songs);
    }

    @Override
    public SongCollectionDto createSongCollection(SongCollectionInputDto songCollectionInputDto) {
        SongCollection songCollection = convertToEntity(songCollectionInputDto);
        SongCollection saved = collectionRepository.save(songCollection); // persisted entity
        SongCollectionDto songCollectionDto = convertToDto(saved);
        return songCollectionDto;
    }

    @Override
    public void deleteSongCollection(Long id) {
        collectionRepository.deleteById(id);
    }


    // todo Update updaten! Haal hem alleen op en saved daarna gelijk weer zonder iets te doen.

    @Override
    public SongCollectionDto updateSongCollection(Long id, SongCollectionInputDto dto) {
        SongCollection collection = collectionRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No collection found with the ID: " + id));
        // Update collection with new DTO values
        collectionRepository.save(collection);
        return convertToDto(collection);
    }


    @Override
    public void addImageToSongCollection(Long collectionId, Image id) {
        // Logic to add image to song collection
    }

//    @Override
//    public void removeSongsFromCollection(Long collectionId, List<Long> songIds) {
//        // Retrieve the song collection by its ID
//        SongCollection collection = collectionRepository.findById(collectionId)
//                .orElseThrow(() -> new RecordNotFoundException("No collection found with the ID: " + collectionId));
//
//        // Retrieve the songs by their IDs
//        List<Song> songsToRemove = songRepository.findAllById(songIds);
//
//        // Remove the songs from the collection
//        collection.getSongs().removeAll(songsToRemove);
//    }

    private SongCollectionDto convertToDto(SongCollection collection) {
        SongCollectionDto dto = new SongCollectionDto(collection.getId(), convertSongListToIdList(collection.getSongs()));

        dto.setSongCollectionTitle(collection.getSongCollectionTitle());
        return dto;
    }


    private List<SongIdDto> convertSongListToIdList(List<Song> songs) {
        List<SongIdDto> idDtos = new ArrayList<>();
        for (Song song : songs) {
            SongIdDto idDto = new SongIdDto();

            idDto.setId(song.getId());
            idDto.setSongTitle(song.getSongTitle());

            idDtos.add(idDto);
        }
        return idDtos;
    }

    private List<Song> convertIdListToSongList(List<Long> longs) {
        List<Song> songs = new ArrayList<>();
        for (Long longId : longs) {
            Song song = songRepository.findById(longId).orElse(null);

            if (song != null) {
                songs.add(song);
            }
        }
        return songs;
    }

    private SongCollection convertToEntity(SongCollectionInputDto dto) {
        SongCollection collection = new SongCollection();

        collection.setSongCollectionTitle(dto.getSongCollectionTitle());
        collection.setSongs(convertIdListToSongList(dto.getSongIds()));
        return collection;
    }

}
