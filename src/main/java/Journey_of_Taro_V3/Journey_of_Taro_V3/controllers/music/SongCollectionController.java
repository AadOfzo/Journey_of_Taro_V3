package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongCollectionDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongCollectionInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.images.ImageService;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.music.SongCollectionService;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.music.SongService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/songCollections")
public class SongCollectionController {

    private final SongService songService;
    private SongCollectionService songCollectionService;

    private final ImageService imageService;

    public SongCollectionController(SongService songService, SongCollectionService songCollectionService, ImageService imageService) {
        this.songService = songService;
        this.songCollectionService = songCollectionService;
        this.imageService = null;
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
    public ResponseEntity<Object> createSongCollection(@Valid @RequestBody SongCollectionInputDto songCollectionInputDto) {
        SongCollectionDto savedSongCollection = songCollectionService.createSongCollection(songCollectionInputDto);
        return ResponseEntity.created(URI.create("/songCollections/" + savedSongCollection.getId())).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSongCollection(@PathVariable Long id) {
        songCollectionService.deleteSongCollection(id);
        return ResponseEntity.noContent().build();
    }
}
