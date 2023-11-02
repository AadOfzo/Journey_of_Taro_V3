package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.music.SongService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // Find all songs by songtitle and artist name
//    @GetMapping("/songs")
//    public ResponseEntity<List<SongDto>> getAllSongs(
//            @RequestParam(value = "songTitle", required = false) Optional<String> songTitle,
//            @RequestParam(value = "artistName", required = false) Optional<String> artistName
//    ) {
//        List<SongDto> dtos;
//
//        if (songTitle.isPresent() && artistName.isPresent()) {
//            dtos = songService.getAllSongsBySongTitleAndArtistName(songTitle.get(), artistName.get());
//        } else if (songTitle.isPresent()) {
//            dtos = songService.getAllSongsBySongTitle(songTitle.get());
//        } else if (artistName.isPresent()) {
//            dtos = songService.getAllSongsByArtistName(artistName.get());
//        } else {
//            dtos = songService.getAllSongs();
//        }
//
//        return ResponseEntity.ok().body(dtos);
//    }


    @GetMapping("/songs/{id}")
    public ResponseEntity<SongDto> getSong(@PathVariable("id") Long id) {

        SongDto song = songService.getSongById(id);

        return ResponseEntity.ok().body(song);
    }
    // Find all song by title
//    @GetMapping("/{songTitle}")
//    public ResponseEntity<SongDto> getSong(@PathVariable("songTitle") String songTitle) {
//
//        SongDto optionalSong = songService.getAllSongs(songTitle);
//
//        return ResponseEntity.ok().body(optionalSong);
//    }

    @PostMapping("/")
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

    // Add a new endpoint for uploading images

}
