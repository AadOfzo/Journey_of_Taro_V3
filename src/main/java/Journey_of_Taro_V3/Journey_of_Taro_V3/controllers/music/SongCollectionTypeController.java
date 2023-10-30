package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.SongCollectionType;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.music.SongCollectionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/songCollectionTypes")
public class SongCollectionTypeController {

    @Autowired
    private SongCollectionTypeService songCollectionTypeService;

    @GetMapping("")
    public ResponseEntity<List<SongCollectionType>> getAllSongCollectionTypes() {
        List<SongCollectionType> songCollectionTypes = songCollectionTypeService.getAllSongCollectionTypes();
        return ResponseEntity.ok().body(songCollectionTypes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongCollectionType> getSongCollectionType(@PathVariable("id") Long id) {
        SongCollectionType songCollectionType = songCollectionTypeService.getSongCollectionTypeById(id);
        return ResponseEntity.ok().body(songCollectionType);
    }

    @PostMapping("")
    public ResponseEntity<Object> addSongCollectionType(@RequestBody SongCollectionType songCollectionType) {
        songCollectionTypeService.saveSongCollectionType(songCollectionType);
        return ResponseEntity.created(null).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSongCollectionType(@PathVariable Long id) {
        songCollectionTypeService.deleteSongCollectionTypeById(id);
        return ResponseEntity.noContent().build();
    }


}
