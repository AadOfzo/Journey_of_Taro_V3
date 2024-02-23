package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.BadRequestException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.RecordNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.CustomMultipartFile;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.users.User;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.music.SongService;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongInputDto;

import Journey_of_Taro_V3.Journey_of_Taro_V3.services.users.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/songs")
public class SongController {
    private final SongService songService;
    private final UserService userService;

    @Autowired
    public SongController(SongService songService, UserService userService) {
        this.songService = songService;
        this.userService = userService;
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
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SongDto> addSong(
            @RequestParam("artistName") String artistName,
            @RequestParam("file") CustomMultipartFile file,
            @RequestPart("songTitle") String songTitle) {
        try {
            // Todo: Move to other method if nesseccary, trigger when
//            // Retrieve the user based on the provided artistName
//            User artist = userService.getUserByArtistName(artistName)
//                    .orElseThrow(() -> new RecordNotFoundException("No user found with artist name: " + artistName));

            // Create a SongInputDto object with the provided data
            SongInputDto inputDto = new SongInputDto();
            inputDto.setSongFile(file);
            inputDto.setSongTitle(songTitle);
//            inputDto.setArtistName(artist);

            // Add the song using the service
            SongDto dto = songService.addSong(inputDto);
            System.out.println("added " + songTitle +" to database");
            return ResponseEntity.created(null).body(dto);
        } catch (IOException e) {
            // Handle the IOException
            // For example:
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
