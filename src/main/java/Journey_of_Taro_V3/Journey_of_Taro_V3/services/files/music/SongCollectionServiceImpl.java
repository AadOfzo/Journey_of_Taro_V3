package Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.*;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.RecordNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.Image;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.Song;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.SongCollection;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music.SongCollectionRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music.SongRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.images.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

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
        for (Song song : songsToAdd) {
            song.setSongCollection(collection); // Set the songCollection on the Song
        }
        collection.getSongs().addAll(songsToAdd);
        collectionRepository.save(collection);
    }

    @Override
    public void removeSongsFromCollection(Long collectionId, List<Long> songIds) {
        SongCollection collection = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new RecordNotFoundException("No collection found with the ID: " + collectionId));
        List<Song> songsToRemove = songRepository.findAllById(songIds);
        collection.getSongs().removeAll(songsToRemove);
        for (Song song : songsToRemove) {
            song.setSongCollection(null);
        }
        collectionRepository.save(collection);
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
        SongCollection collection = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new RecordNotFoundException("No collection found with the ID: " + collectionId));
        List<Song> songs = collection.getSongs();
        return songService.transferSongListToDtoList(songs);
    }

    @Override
    public SongCollectionDto createSongCollection(SongCollectionInputDto songCollectionInputDto) {
        SongCollection songCollection = new SongCollection();
        songCollection.setSongCollectionTitle(songCollectionInputDto.getSongCollectionTitle());
        SongCollection saved = collectionRepository.save(songCollection);
        return convertToDto(saved);
    }

    @Override
    public SongCollectionDto saveSongCollection(SongCollectionInputDto dto) {
        SongCollection songCollection = new SongCollection();
        songCollection.setSongCollectionTitle(dto.getSongCollectionTitle());
        List<Song> songs = songRepository.findAllById(dto.getSongIds());
        for (Song song : songs) {
            song.setSongCollection(songCollection); // Set the songCollection on the Song
        }
        songCollection.setSongs(songs);
        SongCollection saved = collectionRepository.save(songCollection);
        return convertToDto(saved);
    }

    @Override
    public void deleteSongCollection(Long id) {
        collectionRepository.deleteById(id);
    }

    @Override
    public SongCollectionDto updateSongCollection(Long id, SongCollectionInputDto dto) {
        SongCollection collection = collectionRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No collection found with the ID: " + id));
        collection.setSongCollectionTitle(dto.getSongCollectionTitle());
        collectionRepository.save(collection);
        return convertToDto(collection);
    }

    @Override
    public void addImageToSongCollection(Long collectionId, Image image) {
        SongCollection collection = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new RecordNotFoundException("No collection found with the ID: " + collectionId));
        collection.setCollectionImage(image);
        collectionRepository.save(collection);
    }

    @Override
    public SongCollectionDto createFolderAndCopyFiles(Long collectionId) {
        SongCollection collection = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new RecordNotFoundException("No collection found with the ID: " + collectionId));

        String collectionTitle = collection.getSongCollectionTitle();
        Path collectionPath = Paths.get("uploads/music/song_collections/" + collectionTitle);

        try {
            Files.createDirectories(collectionPath);

            for (Song song : collection.getSongs()) {
                Path sourcePath = Paths.get("uploads/music/songs/" + song.getFileName());
                Path targetPath = collectionPath.resolve(song.getFileName());
                Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            }

            String songCollectionUrl = collectionPath.toString();
            collection.setSongCollectionUrl(songCollectionUrl);

            collectionRepository.save(collection);
            return convertToDto(collection);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create folder and copy files", e);
        }
    }

    private SongCollectionDto convertToDto(SongCollection collection) {
        List<SongIdDto> songIdDtos = collection.getSongs().stream()
                .map(song -> new SongIdDto(song.getId(), song.getSongTitle()))
                .collect(Collectors.toList());

        // Map Image to ImageDto
        ImageDto imageDto = null;
        Image image = collection.getCollectionImage();
        if (image != null) {
            imageDto = new ImageDto(
                    image.getId(),
                    image.getImageName(),
                    image.getImageAltName(),
                    image.getImageUrl()
            );
        }

        // Set songCollectionUrl
        String songCollectionUrl = collection.getSongCollectionUrl();

        // Create and return the DTO with all parameters
        return new SongCollectionDto(
                collection.getId(),
                songIdDtos,
                collection.getSongCollectionTitle(),
                imageDto,
                songCollectionUrl
        );
    }
}
