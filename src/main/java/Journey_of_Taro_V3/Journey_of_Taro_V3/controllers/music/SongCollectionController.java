package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongCollectionDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongCollectionInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.SongCollection;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.users.User;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.images.ImageServiceImpl;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.music.SongCollectionService;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.music.SongService;
import org.springframework.core.io.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/songCollections")
public class SongCollectionController {

    private final SongService songService;
    private final SongCollectionService songCollectionService;
    private final ImageServiceImpl imageService;

    public SongCollectionController(SongService songService, SongCollectionService songCollectionService, ImageServiceImpl imageService) {
        this.songService = songService;
        this.songCollectionService = songCollectionService;
        this.imageService = imageService;
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

    @GetMapping("/{id}/image")
    public ResponseEntity<Resource> getSongCollectionImage(@PathVariable("id") Long id, HttpServletRequest request){
        Resource resource = songCollectionService.getImageFromSongCollection(id);

        String mimeType;
        try {
            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=\"" + resource.getFilename() + "\"")
                .body(resource);
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

    @PostMapping("/{id}/image")
    public ResponseEntity<?> addImageToSongCollection(@PathVariable("id") Long id,
                                            @RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty");
            }

            String imageName = imageService.storeFile(file);
            SongCollection songCollection = songCollectionService.assignImageToSongCollection(id, imageName);

            URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/songCollections/{id}/image")
                    .buildAndExpand(id)
                    .toUri();

            System.out.println("Created URI location: " + location.toString());

            return ResponseEntity.created(location).body(songCollection);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not store file");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @PostMapping("/{id}/createFolderAndCopyFiles")
    public ResponseEntity<SongCollectionDto> createFolderAndCopyFiles(@PathVariable Long id) {
        SongCollectionDto songCollectionDto = songCollectionService.createFolderAndCopyFiles(id);
        URI location = URI.create(songCollectionDto.getSongCollectionUrl());
        return ResponseEntity.created(location).body(songCollectionDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSongCollection(@PathVariable Long id) {
        songCollectionService.deleteSongCollection(id);
        return ResponseEntity.noContent().build();
    }
}
