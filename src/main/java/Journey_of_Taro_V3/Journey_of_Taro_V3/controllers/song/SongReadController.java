package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.song;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.RecordNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.music.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/songs")
public class SongReadController {
    private final SongService songService;

    public SongReadController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping
    public List<SongDto> getAllSongs() { return songService.getAllSongs(); }

    @GetMapping("/read/{id}")
    public ResponseEntity<SongDto> getSong(@PathVariable("id") Long id) {
        try {
            SongDto song = songService.getSongById(id);
            return ResponseEntity.ok().body(song);
        } catch (RecordNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
