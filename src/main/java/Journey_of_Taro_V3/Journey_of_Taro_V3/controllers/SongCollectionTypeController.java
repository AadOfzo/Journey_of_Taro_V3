package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.Song;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.SongCollectionType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class SongCollectionTypeController {
    private SongCollectionType songCollectionType;

    @PostMapping("/songCollectionType/{songCollectionTypeId}/song")
    public ResponseEntity<Object> addSongToSongCollectionType(@PathVariable("song_collection_type_id") int songCollectionTypeId, @RequestBody SongDto songDto, @PathVariable String songCollectionTypeId) {
        try {
            Song song = new Song(songDto.getId(), songDto.getSongtitle(), songDto.getArtistname(), songDto.getIsfavorited(), songDto.songCollectionType.get());
            songCollectionType.addSong(song, songCollectionTypeId);
            return ResponseEntity.ok("Song added to song collection type successfully");
        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity.badRequest().body("Invalid song collection type index");
        }
    }
}
