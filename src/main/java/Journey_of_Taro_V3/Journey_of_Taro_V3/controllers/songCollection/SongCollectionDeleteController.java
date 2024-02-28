package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.songCollection;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongCollectionDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongCollectionInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.images.ImageService;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.music.SongCollectionService;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.music.SongService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/songCollections")
public class SongCollectionDeleteController {

    private final SongService songService;
    private SongCollectionService songCollectionService;

    private final ImageService imageService;

    public SongCollectionDeleteController(SongService songService, SongCollectionService songCollectionService, ImageService imageService) {
        this.songService = songService;
        this.songCollectionService = songCollectionService;
        this.imageService = imageService;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteSongCollection(@PathVariable Long id) {
        try {
            songCollectionService.deleteSongCollection(id);
            return ResponseEntity.ok("Collection deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete song: " + e.getMessage());
        }
    }
}
