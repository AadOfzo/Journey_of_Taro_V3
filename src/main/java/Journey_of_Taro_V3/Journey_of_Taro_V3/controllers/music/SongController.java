package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.RecordNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.music.SongService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping(value = "songs")
public class SongController {

    private final SongService songService;
    private final Environment environment;

    @Autowired
    public SongController(SongService songService, Environment environment) {
        this.songService = songService;
        this.environment = environment;
    }

    @GetMapping
    public List<SongDto> getAllSongs() {
        return songService.getAllSongs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongDto> getSong(@PathVariable("id") Long id) {
        try {
            SongDto song = songService.getSongById(id);
            return ResponseEntity.ok().body(song);
        } catch (RecordNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/song/{songTitle}")
    public ResponseEntity<String> getSongUrl(@PathVariable("songTitle") String songTitle) {
        String songUrl = songService.getSongUrlByTitle(songTitle);
        return ResponseEntity.ok().body(songUrl);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateSong(@PathVariable Long id, @Valid @RequestBody SongInputDto newSong) {
        SongDto dto = songService.updateSong(id, newSong);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SongDto> addSong(
            @RequestParam("file") MultipartFile file,
            @RequestParam("artistName") String artistName,
            @RequestParam("songTitle") String songTitle,
            HttpServletRequest request) {
        try {
            String songUrl = storeFileAndGetUrl(file, "uploads/songs");

            SongInputDto inputDto = new SongInputDto();
            inputDto.setSongFile(file);
            inputDto.setSongTitle(songTitle);
            inputDto.setArtistName(artistName);
            inputDto.setSongUrl(songUrl);

            SongDto dto = songService.addSong(inputDto);

            System.out.println("Added " + songTitle + " to the database.");

            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (IOException e) {
            throw new RuntimeException("Failed to process the uploaded file.", e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
        return ResponseEntity.noContent().build();
    }

    private String storeFileAndGetUrl(MultipartFile file, String uploadDir) throws IOException {
        // Ensure the upload directory exists
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate a unique file name
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        // Path to save the file
        Path filePath = uploadPath.resolve(fileName);

        // Copy the file to the upload directory
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Construct the URL to access the file
        String baseUrl = environment.getProperty("base.url", "http://localhost:8080");
        String fileUrl = baseUrl + "/files/" + fileName;

        return fileUrl;
    }
}
