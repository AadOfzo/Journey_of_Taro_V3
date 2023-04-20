package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.SongInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.SongService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(value = "/songs")
public class SongController {

    private final SongService songService;


    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<SongDto>> getSongs() {

        List<SongDto> songDtos = songService.getAllSongs();

        return ResponseEntity.ok().body(songDtos);
    }

    @GetMapping("/songs")
    public ResponseEntity<List<SongDto>> getAllSongs(@RequestParam(value = "songtitle", required = false) Optional<String> songtitle) {
        List<SongDto> dtos;

        if (songtitle.isEmpty()) {

            dtos = songService.getAllSongs();

        } else {

            dtos = songService.getAllSongsBySongTitle(songtitle.get());

        }

        return ResponseEntity.ok().body(dtos);
    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<SongDto> getSong(@PathVariable("id") Long id) {

        SongDto song = songService.getSongById(id);

        return ResponseEntity.ok().body(song);
    }

    @GetMapping("/{songtitle}")
    public ResponseEntity<SongDto> getSong(@PathVariable("songtitle") String songtitle) {

        SongDto optionalSong = songService.getSong(songtitle);

        return ResponseEntity.ok().body(optionalSong);
    }

    @PostMapping("/songs")
    public ResponseEntity<Object> addSong(@Valid @RequestBody SongInputDto songInputDto) {

        SongDto dto = songService.addSong(songInputDto);

        return ResponseEntity.created(null).body(dto);
    }

    @DeleteMapping("songs/{id}")
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
