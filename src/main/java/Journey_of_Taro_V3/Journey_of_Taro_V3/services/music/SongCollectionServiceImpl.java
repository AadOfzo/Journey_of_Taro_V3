package Journey_of_Taro_V3.Journey_of_Taro_V3.services.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongCollectionDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongCollectionInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.RecordNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.Image;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.SongCollectionType;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.Song;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.SongCollection;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.images.ImageRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music.SongCollectionRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music.SongRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.images.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongCollectionServiceImpl implements SongCollectionService {

    private final SongCollectionRepository collectionRepository;
    private final SongRepository songRepository;
    private final ImageRepository imageRepository;

    @Autowired
    public SongCollectionServiceImpl(SongCollectionRepository collectionRepository, SongRepository songRepository, ImageRepository imageRepository) {
        this.collectionRepository = collectionRepository;
        this.songRepository = songRepository;
        this.imageRepository = imageRepository;
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
    public SongCollectionDto createSongCollection(
            SongCollectionInputDto dto,
            MultipartFile imageFile,
            List<MultipartFile> songFiles) {

        SongCollection collection = transferToSongCollection(dto);
        Image image = convertToImage(dto.getImage());

        // Save image to the collection
        addImageToSongCollection(collection.getId(), image);

        // Save songs to the collection
        addSongsToSongCollection(collection.getId(), songFiles);

        collectionRepository.save(collection);
        return transferToSongCollectionDto(collection);
    }

//    @Override
//    public SongCollectionDto createSongCollection(SongCollectionInputDto dto, MultipartFile imageFile, List<MultipartFile> songFile) {
//    SongCollection collection = transferToSongCollection(dto);
//
//    Image image = convertToImage((ImageDto) imageFile);
//    collection.setImage(image);
//
//    List<SongDto> songDtos = dto.getSongs();
//    List<Song> songs = convertToSongs(songDtos, songFile, collection);
//
//    songRepository.saveAll(songs);
//    collectionRepository.save(collection);
//
//    return transferToSongCollectionDto(collection);
//}

    private void addSongsToSongCollection(Long collectionId, List<MultipartFile> songFiles) {
        SongCollection collection = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new RecordNotFoundException("No collection found with the ID: " + collectionId));

        List<Song> songs = songFiles.stream()
                .map(file -> new Song(file.getOriginalFilename(), file, "Unknown Artist", "EP"))
                .collect(Collectors.toList());

        songs.forEach(song -> song.setSongCollection(collection));
        songRepository.saveAll(songs);
        collection.setSongs(songs);
        collectionRepository.save(collection);
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
//        if(songCollection.getSong() != null){
//            dto.setSongDto(SongService.transferToDto(songCollection.getSong()));
//        }
        return dto;
    }

    private List<SongCollectionDto> transferCollectionListToDtoList(List<SongCollection> collections) {
        return collections.stream().map(this::transferToSongCollectionDto).collect(Collectors.toList());
    }

//    private SongCollection transferToSongCollection(SongCollectionInputDto dto) {
//        // Assuming you have a constructor in SongCollection that takes List<Song>, String, and Image as parameters
//        List<SongDto> songDtos = dto.getSongs();
//        List<Song> songs = convertToSongs(songDtos);
//
//        ImageDto imageDto = dto.getImage();
//        Image image = convertToImage(imageDto);
//
//        // Assuming you have a constructor in SongCollection that takes List<Song>, String, and Image as parameters
//        return new SongCollection(songs, dto.getSongCollectionTitle(), image);
//    }

    private SongCollection transferToSongCollection(SongCollectionInputDto dto){
        var songCollection = new SongCollection();

        songCollection.setId(songCollection.getId());
        songCollection.setSongCollectionTitle(dto.getSongCollectionTitle());

        return songCollection;
    }

    private List<Song> convertToSongs(List<SongDto> songDtos, List<MultipartFile> songFiles, SongCollection songCollection) {
        List<Song> songs = new ArrayList<>();

        for (int i = 0; i < songDtos.size(); i++) {
            SongDto songDto = songDtos.get(i);
            MultipartFile songFile = songFiles.get(i);

            Song song = new Song();
            song.setSongTitle(songDto.getSongTitle());
            song.setArtistName(songDto.getArtistName());
            song.setSongCollection(songCollection);
            // other fields...

            try {
                byte[] songData = songFile.getBytes();
                song.setSongData(songData);
            } catch (IOException e) {
                throw new RuntimeException("Failed to read song file data", e);
            }

            songs.add(song);
        }

        return songs;
    }

    private Image convertToImage(ImageDto imageDto) {
        // Implement logic to convert an ImageDto to an Image
        // Return the image
        return new Image();  // Placeholder, replace with actual logic
    }

}
