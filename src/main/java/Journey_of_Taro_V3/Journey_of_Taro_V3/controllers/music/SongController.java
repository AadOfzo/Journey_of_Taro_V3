package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.music;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.music.SongService;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongInputDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/songs")
public class SongController {

    private final SongService songService;

    @Autowired
    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping
    public List<SongDto> getAllSongs() { return songService.getAllSongs(); }

    @GetMapping("/{id}")
    public ResponseEntity<SongDto> getSongbyId(@PathVariable("id") Long id) {
        SongDto song = songService.getSongById(id);
        return ResponseEntity.ok().body(song);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SongDto> addSong(
            @RequestParam("file") MultipartFile file,
            @RequestParam("songTitle") String songTitle) {
        SongInputDto inputDto = new SongInputDto();
        inputDto.setFile(file);
        inputDto.setSongTitle(songTitle);

        SongDto dto = songService.addSong(inputDto);
        return ResponseEntity.created(null).body(dto);
    }


    @DeleteMapping("/songs/{id}")
    public ResponseEntity<Object> deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
        return ResponseEntity.noContent().build();
    }


//    @PutMapping("/songs/{id}")
//    public ResponseEntity<Object> updateSong(@PathVariable Long id, @Valid @RequestBody SongInputDto newSong) {
//
//        SongDto dto = songService.updateSong(id, newSong);
//
//        return ResponseEntity.ok().body(dto);
//    }

}
