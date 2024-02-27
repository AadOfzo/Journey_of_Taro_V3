package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.song;

import Journey_of_Taro_V3.Journey_of_Taro_V3.services.song.SongDeleteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/songs")
public class SongDeleteController {
    private final SongDeleteService songDeleteService;

    public SongDeleteController(SongDeleteService songDeleteService) {
        this.songDeleteService = songDeleteService;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSong(@PathVariable Long id) {
        try {
            songDeleteService.deleteSong(id);
            return ResponseEntity.ok("Song deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete song: " + e.getMessage());
        }
    }
}
