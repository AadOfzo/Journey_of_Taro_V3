package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.songCollection;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongCollectionDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongCollectionInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.images.ImageService;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.music.SongCollectionService;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.music.SongService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/songCollections")
public class SongCollectionCreateController {

    private final SongService songService;
    private final SongCollectionService songCollectionService;

    private final ImageService imageService;

    public SongCollectionCreateController(SongService songService, SongCollectionService songCollectionService, ImageService imageService) {
        this.songService = songService;
        this.songCollectionService = songCollectionService;
        this.imageService = null;
    }

    @PostMapping("create")
    public ResponseEntity<Object> createSongCollection(
            @Valid @RequestBody SongCollectionInputDto songCollectionInputDto,
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam("songFiles") List<MultipartFile> songFiles) {

        SongCollectionDto savedSongCollection = songCollectionService.createSongCollection(songCollectionInputDto, imageFile, songFiles);
        return ResponseEntity.created(URI.create("/songCollections/" + savedSongCollection.getId())).build();
    }

}
