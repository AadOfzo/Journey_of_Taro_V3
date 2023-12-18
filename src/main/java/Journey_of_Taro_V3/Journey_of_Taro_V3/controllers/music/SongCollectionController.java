//package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.music;
//
//import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongCollectionDto;
//import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.SongCollectionType;
//import Journey_of_Taro_V3.Journey_of_Taro_V3.services.music.SongCollectionService;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.net.URI;
//import java.util.List;
//
//@RestController
//@RequestMapping(value = "/songCollections")
//public class SongCollectionController {
//
//    @Autowired
//    private SongCollectionService songCollectionService;
//
//    @GetMapping("")
//    public ResponseEntity<List<SongCollectionDto>> getAllSongCollections() {
//        List<SongCollectionDto> songCollections = songCollectionService.getAllCollections();
//        return ResponseEntity.ok().body(songCollections);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<SongCollectionDto> getSongCollection(@PathVariable("id") Long id) {
//        SongCollectionDto songCollection = songCollectionService.getCollectionById(id);
//        return ResponseEntity.ok().body(songCollection);
//    }
//
//    @PostMapping("")
//    public ResponseEntity<Object> addSongCollection(@Valid @RequestBody SongCollectionDto songCollectionDto) {
//        SongCollectionDto savedSongCollection = songCollectionService.addCollection(songCollectionDto);
//        return ResponseEntity.created(URI.create("/songCollections/" + savedSongCollection.getId())).build();
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Object> deleteSongCollection(@PathVariable Long id) {
//        songCollectionService.deleteCollection(id);
//        return ResponseEntity.noContent().build();
//    }
//}
