package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.song;

import Journey_of_Taro_V3.Journey_of_Taro_V3.services.music.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/songs")
public class SongDeleteController {

    private final SongService songService;

    public SongDeleteController(SongService songService) {
        this.songService = songService;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
        return ResponseEntity.noContent().build();
    }
}
