package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.RecordNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.music.SongService;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.users.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
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
    public SongController(SongService songService, UserService userService, Environment environment) {
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

    // Get Songfile with URL
    @GetMapping("/song/{songTitle}")
    public ResponseEntity<String> getSongUrl(@PathVariable("songTitle") String songTitle) {
        String songUrl = songService.getSongUrlByTitle(songTitle);
        return ResponseEntity.ok().body(songUrl);
    }

    // Get Songfile with bytes
//    @GetMapping("/song/{songTitle}")
//    public ResponseEntity<byte[]> getSongFile(@PathVariable("songTitle") String songTitle) {
//        Song song = songService.getSongWithData(songTitle);
//
//        MediaType mediaType;
//
//        try {
//            mediaType = MediaType.parseMediaType(song.getMimeType());
//        } catch (InvalidMediaTypeException ignore) {
//            mediaType = MediaType.APPLICATION_OCTET_STREAM;
//        }
//
//        return ResponseEntity
//                .ok()
//                .contentType(mediaType)
//                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=\"" + song.getSongTitle() + "\"")
//                .body(song.getSongData());
//    }

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
            String songUrl = storeFileAndGetUrl(file);
            // Create a SongInputDto object with the provided data
            SongInputDto inputDto = new SongInputDto();
            inputDto.setSongFile(file);
            inputDto.setSongTitle(songTitle);
            inputDto.setArtistName(artistName);
            inputDto.setSongUrl(songUrl);

            // Add the song using the service
            SongDto dto = songService.addSong(inputDto);

            // Log the success message
            System.out.println("Added " + songTitle + " to the database.");

            // Return a 201 CREATED response
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

    // Method to store file and return URL
    private String storeFileAndGetUrl(MultipartFile file) throws IOException {
        // Directory where you want to store the uploaded files
        String uploadDir = "/songs";

        // Create the upload directory if it doesn't exist
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
        String fileUrl = environment.getProperty("base.url") + "/files/" + fileName;

        return fileUrl;
    }
}
