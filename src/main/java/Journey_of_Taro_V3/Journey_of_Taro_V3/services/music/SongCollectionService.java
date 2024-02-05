package Journey_of_Taro_V3.Journey_of_Taro_V3.services.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongCollectionDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongCollectionInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.Image;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.Song;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SongCollectionService {
    List<SongCollectionDto> getAllSongCollections();
    SongCollectionDto getSongCollectionById(Long id);
    SongCollectionDto createSongCollection(SongCollectionInputDto dto, MultipartFile imageFile, List<MultipartFile> songFiles);
    void deleteSongCollection(Long id);
    SongCollectionDto updateSongCollection(Long id, SongCollectionInputDto dto);

    void addImageToSongCollection(Long collectionId, Image image);
}

