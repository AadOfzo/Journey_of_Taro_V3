package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.song;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.CustomMultipartFile;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.song.SongCreateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("song")
public class SongCreateController {
    private final SongCreateServiceImpl create;
    @Autowired
    public SongCreateController(SongCreateServiceImpl create) {
        this.create = create;
    }
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SongDto> create(
            @RequestParam("file") MultipartFile file,
            @RequestPart("title") String title) {
        try {
            SongInputDto inputDto = new SongInputDto();
            inputDto.setSongFile(file);
            inputDto.setSongTitle(title);

            SongDto dto = create.create(inputDto);
            System.out.println("added " + title +" to database");
            return ResponseEntity.created(null).body(dto);
        } catch (Exception e) {
            throw new RuntimeException("Failed to process the uploaded file.", e);
        }
    }

}
