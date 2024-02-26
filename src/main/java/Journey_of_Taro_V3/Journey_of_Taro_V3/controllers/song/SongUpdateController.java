package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.song;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.music.SongService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/songs")
public class SongUpdateController {
    private final SongService songService;

    public SongUpdateController(SongService songService) {
        this.songService = songService;
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateSong(@PathVariable Long id, @Valid @RequestBody SongInputDto newSong) {

        SongDto dto = songService.updateSong(id, newSong);

        return ResponseEntity.ok().body(dto);
    }
}
