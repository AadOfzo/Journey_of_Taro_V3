package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.songCollection;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongCollectionDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.images.ImageService;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.music.SongCollectionService;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.music.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/songCollections")
public class SongCollectionReadController {

    private final SongService songService;
    private SongCollectionService songCollectionService;

    public SongCollectionReadController(SongService songService, SongCollectionService songCollectionService, ImageService imageService) {
        this.songService = songService;
        this.songCollectionService = songCollectionService;
    }

    @GetMapping("read")
    public ResponseEntity<List<SongCollectionDto>> getAllSongCollections() {
        List<SongCollectionDto> songCollections = songCollectionService.getAllSongCollections();
        return ResponseEntity.ok().body(songCollections);
    }

    @GetMapping("read/{id}")
    public ResponseEntity<SongCollectionDto> getSongCollection(@PathVariable("id") Long id) {
        SongCollectionDto songCollection = songCollectionService.getSongCollectionById(id);
        return ResponseEntity.ok().body(songCollection);
    }

}
