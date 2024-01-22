package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.SongCollectionType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("song_collection_type_mapping")
public class SongCollectionTypeController {
    @GetMapping("/get")
    public String getBySongCollectionType(@RequestParam(required = true) SongCollectionType songCollectionType) {
        return songCollectionType.name();
    }
}
