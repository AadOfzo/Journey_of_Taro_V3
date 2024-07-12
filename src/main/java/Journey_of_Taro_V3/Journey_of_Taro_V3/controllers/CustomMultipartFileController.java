package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.CustomMultipartFile;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.images.ImageService;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.music.SongService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
public class CustomMultipartFileController {

    private static final Logger logger = LoggerFactory.getLogger(CustomMultipartFileController.class);
    private final ImageService imageService;
    private final SongService songService;

    private final Environment environment;

    @Autowired
    public CustomMultipartFileController(ImageService imageService, SongService songService, Environment environment) {
        this.imageService = imageService;
        this.songService = songService;
        this.environment = environment;
    }

    @PostMapping(value = "/fileUpload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fileUploadController(@RequestParam("file") CustomMultipartFile customFile,
                                                  @RequestParam("artistName") String artistName,
                                                  @RequestParam("songTitle") String songTitle,
                                                  @RequestParam("userName") String userName) {
        try {
            if (customFile.isEmpty()) {
                throw new IllegalArgumentException("File is empty");
            }

            String fileType = determineFileType(customFile.getContentType());

            if (fileType == null) {
                throw new IllegalArgumentException("Unsupported file type");
            }

            String originalFilename = customFile.getOriginalFilename();

            logger.info("Received file: {}", originalFilename);
            logger.info("File size: {} bytes", customFile.getSize());
            logger.info("File type: {}", fileType);

            if ("Image".equalsIgnoreCase(fileType)) {
                String imageUrl = storeFileAndGetUrl(customFile, "uploads/images");

                ImageInputDto inputDto = new ImageInputDto();
                inputDto.setImageFile(customFile);
                inputDto.setImageName(originalFilename);
                inputDto.setImageAltName(originalFilename);

                ImageDto dto = imageService.addImage(inputDto);
                return ResponseEntity.ok().body(dto);

            } else if ("Audio".equalsIgnoreCase(fileType)) {
                String songUrl = storeFileAndGetUrl(customFile, "uploads/songs");

                // Process audio file:
                SongInputDto inputDto = new SongInputDto();
                inputDto.setSongFile(customFile);
                inputDto.setSongTitle(songTitle == null ? originalFilename : songTitle);
                inputDto.setArtistName(artistName);
                inputDto.setFileName(originalFilename);
                inputDto.setFileSize(customFile.getSize());
                inputDto.setUploadTime(LocalDateTime.now());
                inputDto.setSongUrl(songUrl);

                SongDto dto = songService.addSong(inputDto);
                return ResponseEntity.ok().body(dto);
            } else {
                throw new IllegalArgumentException("Unsupported file type");
            }
        } catch (IllegalArgumentException | IOException e) {
            logger.error("Error occurred while processing file upload", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    private String determineFileType(String contentType) {
        if (contentType != null && contentType.startsWith("image")) {
            return "Image";
        } else if (contentType != null && contentType.startsWith("audio")) {
            return "Audio";
        } else {
            return "Unknown";
        }
    }

    private String storeFileAndGetUrl(CustomMultipartFile file, String uploadDir) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        Path filePath = uploadPath.resolve(fileName);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        String baseUrl = environment.getProperty("base.url", "http://localhost:8080");
        String fileUrl = baseUrl + "/files/" + fileName;

        return fileUrl;
    }
}
