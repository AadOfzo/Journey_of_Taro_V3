package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongCollectionDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongCollectionInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.CustomMultipartFile;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.Song;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.SongCollection;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.images.ImageService;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.music.SongCollectionService;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.music.SongService;
import io.jsonwebtoken.io.IOException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//@RestController
//@RequestMapping(value = "/songCollections")
public class SongCollectionController {

    private final SongService songService;
    private final SongCollectionService songCollectionService;

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

    // Method to add Image to SongCollection


    @PostMapping("")
    public ResponseEntity<Object> createSongCollection(
            @RequestParam("songIds") List<Long> songIds) {

        try {
            SongCollectionInputDto songCollectionInputDto = new SongCollectionInputDto(songIds);
            SongCollectionDto songCollectionDto = songCollectionService.createSongCollection(songCollectionInputDto);

            // Return response with created song collection DTO
            return ResponseEntity.created(URI.create("/songCollections/" + songCollectionDto.getId())).build();
        } catch (IOException e) {
            // Handle file processing exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process song files");
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSongCollection(@PathVariable Long id) {
        songCollectionService.deleteSongCollection(id);
        return ResponseEntity.noContent().build();
    }
}
