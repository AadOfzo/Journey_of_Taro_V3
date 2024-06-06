package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongCollectionDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongCollectionInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.music.SongCollectionService;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.music.SongService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/songCollections")
public class SongCollectionController {

    private final SongService songService;
    private final SongCollectionService songCollectionService;

    public SongCollectionController(SongService songService, SongCollectionService songCollectionService) {
        this.songService = songService;
        this.songCollectionService = songCollectionService;
    }

    @GetMapping("")
    public ResponseEntity<List<SongCollectionDto>> getAllSongCollections() {
        List<SongCollectionDto> songCollections = songCollectionService.getAllSongCollections();
        return ResponseEntity.ok().body(songCollections);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongCollectionDto> getSongCollection(@PathVariable("id") Long id) {
        SongCollectionDto songCollection = songCollectionService.getSongCollectionById(id);
        return ResponseEntity.ok().body(songCollection);
    }

    @PostMapping("")
    public ResponseEntity<SongCollectionDto> createSongCollection(@RequestBody SongCollectionInputDto songCollectionInputDto) {
        SongCollectionDto songCollectionDto = songCollectionService.createSongCollection(songCollectionInputDto);
        URI location = URI.create("/songCollections/" + songCollectionDto.getId());
        return ResponseEntity.created(location).body(songCollectionDto);
    }

    @PostMapping("/{id}/songs")
    public ResponseEntity<Void> addSongsToCollection(@PathVariable Long id, @RequestBody List<Long> songIds) {
        songCollectionService.addSongsToCollection(id, songIds);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSongCollection(@PathVariable Long id) {
        songCollectionService.deleteSongCollection(id);
        return ResponseEntity.noContent().build();
    }
}
