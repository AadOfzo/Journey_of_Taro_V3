package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.SongCollectionDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.SongCollectionNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.SongTitleNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.Song;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.SongCollection;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.SongCollectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/song-collections")
public class SongCollectionController {

    private final SongCollectionService songCollectionService;

    public SongCollectionController(SongCollectionService songCollectionService) {
        this.songCollectionService = songCollectionService;
    }

    @PostMapping("/{songCollectionId}/songs/{songId}")
    public ResponseEntity<Void> addSongToCollection(@PathVariable Long songCollectionId, @PathVariable Long songId) {
        try {
            songCollectionService.addSongToCollection(songCollectionId, songId);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (SongCollectionNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (SongTitleNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Helper methods to convert between SongCollection and SongCollectionDto

    private SongCollectionDto convertToDto(SongCollection songCollection) {
        SongCollectionDto songCollectionDto = new SongCollectionDto();
        songCollectionDto.setId(songCollection.getId());
        songCollectionDto.setCollectionname(songCollection.getName());
        songCollectionDto.setSongs(songCollection.getSong().stream()
                .map(song -> convertSongToDto(song))
                .collect(Collectors.toList()));
        return songCollectionDto;
    }


    private SongDto convertSongToDto(Song song) {
        SongDto songDto = new SongDto();
        songDto.setId(song.getId());
        songDto.setSongtitle(song.getSongtitle());
        return songDto;
    }

    private List<SongCollectionDto> convertToDtoList(List<SongCollection> songCollections) {
        return songCollections.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<SongCollectionDto> createSongCollection(@RequestBody SongCollectionDto songCollectionDto) {
        SongCollection createdSongCollection = songCollectionService.createSongCollection(songCollectionDto);
        SongCollectionDto createdSongCollectionDto = convertToDto(createdSongCollection);
        return new ResponseEntity<>(createdSongCollectionDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongCollectionDto> getSongCollectionById(@PathVariable Long id) {
        SongCollection songCollection = null;
        try {
            songCollection = songCollectionService.getSongCollectionById(id);
        } catch (SongCollectionNotFoundException e) {
            throw new RuntimeException(e);
        }
        SongCollectionDto songCollectionDto = convertToDto(songCollection);
        return new ResponseEntity<>(songCollectionDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<SongCollectionDto>> getAllSongCollections() {
        List<SongCollection> songCollections = songCollectionService.getSongCollections();
        List<SongCollectionDto> songCollectionDtoList = convertToDtoList(songCollections);
        return new ResponseEntity<>(songCollectionDtoList, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SongCollectionDto> updateSongCollection(@PathVariable Long id, @RequestBody SongCollectionDto songCollectionDto) {
        try {
            SongCollection updatedSongCollection = songCollectionService.updateSongCollection(id, songCollectionDto);
            SongCollectionDto updatedSongCollectionDto = convertToDto(updatedSongCollection);
            return new ResponseEntity<>(updatedSongCollectionDto, HttpStatus.OK);
        } catch (SongCollectionNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSongCollection(@PathVariable Long id) {
        try {
            songCollectionService.deleteSongCollection(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (SongCollectionNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
