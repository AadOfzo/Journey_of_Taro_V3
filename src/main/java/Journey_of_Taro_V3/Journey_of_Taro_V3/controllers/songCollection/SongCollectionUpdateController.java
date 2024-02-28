package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.songCollection;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongCollectionDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongCollectionInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.images.ImageService;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.music.SongCollectionService;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.music.SongService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/songCollections")
public class SongCollectionUpdateController {

    private final SongService songService;
    private SongCollectionService songCollectionService;

    private final ImageService imageService;

    public SongCollectionUpdateController(SongService songService, SongCollectionService songCollectionService, ImageService imageService) {
        this.songService = songService;
        this.songCollectionService = songCollectionService;
        this.imageService = null;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateSongCollection(@PathVariable Long id, @Valid @RequestBody SongCollectionInputDto newSongCollection) {

        SongCollectionDto dto = songCollectionService.updateSongCollection(id, newSongCollection);

        return ResponseEntity.ok().body(dto);
    }
}
