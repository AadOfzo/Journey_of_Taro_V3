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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/songs")
public class SongController {
    private final SongService songService;

    public Environment environment;

    @Autowired
    public SongController(SongService songService, UserService userService) {
        this.songService = songService;
    }

    @GetMapping
    public List<SongDto> getAllSongs() { return songService.getAllSongs(); }

    @GetMapping("/{id}")
    public ResponseEntity<SongDto> getSong(@PathVariable("id") Long id) {
        try {
            SongDto song = songService.getSongById(id);
            return ResponseEntity.ok().body(song);
        } catch (RecordNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }    // Method to store file and return URL
    private String storeFileAndGetUrl(MultipartFile file) throws java.io.IOException {

        String fileName = file.getOriginalFilename();
        String fileUrl = environment.getProperty("base.url") + "/files/" + fileName;

        return fileUrl;
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
            inputDto.setArtistName(artistName); // Set the artistName

            // Add the song using the service
            SongDto dto = songService.addSong(inputDto);

            // Log the success message
            System.out.println("Added " + songTitle + " to the database.");

            // Return a 201 CREATED response with the created song DTO
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (IOException e) {
            // Handle the IOException
            throw new RuntimeException("Failed to process the uploaded file.", e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/songs/{id}")
    public ResponseEntity<Object> updateSong(@PathVariable Long id, @Valid @RequestBody SongInputDto newSong) {

        SongDto dto = songService.updateSong(id, newSong);

        return ResponseEntity.ok().body(dto);
    }

}
