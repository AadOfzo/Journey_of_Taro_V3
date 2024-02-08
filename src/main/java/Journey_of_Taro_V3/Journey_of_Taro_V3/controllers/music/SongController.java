package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.music;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.BadRequestException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.CustomMultipartFile;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.music.SongService;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongInputDto;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SongDto> addSong(
            @RequestPart("songFile") CustomMultipartFile songFile,
            @RequestPart("songTitle") String songTitle) {
        if (!(songFile instanceof CustomMultipartFile)) {
            // Handle the case where the received file is not a CustomMultipartFile
            // You may throw an exception or handle it based on your requirements
            // For example:
             throw new BadRequestException("Invalid file type. Please provide a CustomMultipartFile.");
        }

        CustomMultipartFile customMultipartFile = (CustomMultipartFile) songFile;

        SongInputDto inputDto = new SongInputDto();
        inputDto.setSongFile(customMultipartFile);
        inputDto.setSongTitle(songTitle);

        SongDto dto = songService.addSong(inputDto);
        return ResponseEntity.created(null).body(dto);
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
