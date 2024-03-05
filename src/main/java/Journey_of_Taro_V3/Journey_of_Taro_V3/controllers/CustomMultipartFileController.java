package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.CustomMultipartFile;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.users.User;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.images.ImageService;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.music.SongService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class CustomMultipartFileController {

    private static final Logger logger = LoggerFactory.getLogger(CustomMultipartFileController.class);
    private final ImageService imageService;
    private final SongService songService;

    @Autowired
    public CustomMultipartFileController(ImageService imageService, SongService songService) {
        this.imageService = imageService;
        this.songService = songService;
    }

    @PostMapping(value = "/fileUpload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fileUploadController(MultipartFile file) {
        try {
            // Check if the file is empty
            if (file.isEmpty()) {
                throw new IllegalArgumentException("File is empty");
            }

            // Bepaal file type
            String fileType = determineFileType(file.getContentType());


            if (fileType == null) {
                throw new IllegalArgumentException("Unsupported file type");
            }

            // Get the original file name
            String originalFilename = file.getOriginalFilename();

            // Log file information
            logger.info("Received file: {}", originalFilename);
            logger.info("File size: {} bytes", file.getSize());
            logger.info("File type: {}", fileType);


            // SongCreateService object based on file type
            if ("Image".equalsIgnoreCase(fileType)) {

                // Process image file:
                ImageInputDto inputDto = new ImageInputDto();
                inputDto.setImageFile(file);
                inputDto.setImageName(originalFilename); // Assign original file name to imageName
                inputDto.setImageAltName(originalFilename); // Assign original file name to imageAltName for now
                ImageDto dto = imageService.addImage(inputDto);
                return ResponseEntity.ok().body(dto);

            } else if ("Audio".equalsIgnoreCase(fileType)) {

                // Process audio file:
                CustomMultipartFile customFile = new CustomMultipartFile(originalFilename, file.getContentType(), file.getBytes()); // SongCreateService CustomMultipartFile
                SongInputDto inputDto = new SongInputDto();
                inputDto.setSongFile(customFile);
                inputDto.setSongTitle(originalFilename);
                SongDto dto = songService.addSong(inputDto);
                return ResponseEntity.ok().body(dto);  // Return the DTO
            } else {
                throw new IllegalArgumentException("Unsupported file type");
            }
        } catch (IllegalArgumentException e) {

            logger.error("Error occurred while processing file upload", e);
            throw new RuntimeException("Error occurred while processing file upload", e);
        } catch (IOException e) {

            logger.error("Error occurred while processing file", e);
            throw new RuntimeException("Error occurred while processing file", e);
        }
    }

    // Method voor content type:
    public String determineFileType(String contentType) {
        if (contentType != null && contentType.startsWith("image")) {
            return "Image";
        } else if (contentType != null && contentType.startsWith("audio")) {
            return "Audio";
        } else {
            return "Unknown";
        }
    }

    // POST Voor Images:
    @PostMapping("/images")
    public ResponseEntity<ImageDto> addImage(
            @RequestParam("file") CustomMultipartFile file,
            @RequestParam(value = "imageTitle", required = false) String imageTitle,
            @RequestParam(value = "imageAltName", required = false) String imageAltName) {
        if (imageTitle == null || imageTitle.isEmpty()) {
            imageTitle = file.getOriginalFilename(); // Assign original file name if imageTitle is not provided
        }

        if (imageAltName == null || imageAltName.isEmpty()) {
            imageAltName = imageTitle;
        }

        ImageInputDto inputDto = new ImageInputDto();
        inputDto.setImageFile(file);
        inputDto.setImageName(imageTitle);
        inputDto.setImageAltName(imageAltName);

        ImageDto dto = imageService.addImage(inputDto);
        return ResponseEntity.created(null).body(dto);
    }

    // POST Voor Audio:
    @PostMapping("/songs")
    public ResponseEntity<SongDto> addSong(
            @RequestParam("file") CustomMultipartFile file,
            @RequestParam(value = "songTitle", required = false) String songTitle,
            @RequestParam(value = "artist", required = false) User artist) {
        try {
            if (songTitle == null || songTitle.isEmpty()) {
                songTitle = file.getOriginalFilename(); // Assign original file name if songTitle is not provided
            }

            SongInputDto inputDto = new SongInputDto();
            inputDto.setSongFile(file);
            inputDto.setSongTitle(songTitle);
            inputDto.setArtistName(artist.getArtistName());

            SongDto dto = songService.addSong(inputDto);
            return ResponseEntity.created(null).body(dto);
        } catch (IOException e) {
            // Handle IOException
            logger.error("Error with processing file", e);
            throw new RuntimeException("Error with processing file", e);
        }
    }


}

