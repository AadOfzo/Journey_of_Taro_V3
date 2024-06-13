package Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongCollectionDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongCollectionInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.Image;

import java.util.List;

public interface SongCollectionService {

    void addSongsToCollection(Long collectionId, List<Long> songIds);
    List<SongDto> getSongsByCollection(Long collectionId);
    void removeSongsFromCollection(Long collectionId, List<Long> songIds);

    List<SongCollectionDto> getAllSongCollections();
    SongCollectionDto getSongCollectionById(Long id);
    SongCollectionDto createSongCollection(SongCollectionInputDto dto);
    void deleteSongCollection(Long id);
    SongCollectionDto updateSongCollection(Long id, SongCollectionInputDto dto);

    void addImageToSongCollection(Long collectionId, Image image);

    // This method should be declared in the interface
    SongCollectionDto saveSongCollection(SongCollectionInputDto dto);
    SongCollectionDto createFolderAndCopyFiles(Long collectionId);

}
