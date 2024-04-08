package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.images;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.RecordNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.CustomMultipartFile;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.images.ImageService;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/images")
public class ImageController {

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    public Environment environment;

    @GetMapping
    public List<ImageDto> getAllImages() {
        return imageService.getAllImages();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageDto> getImage(@PathVariable("id") Long id) {
        try {
            ImageDto image = imageService.getImageById(id);
            return ResponseEntity.ok().body(image);
        } catch (RecordNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addImage(
            @RequestParam("file") CustomMultipartFile file,
            @RequestParam("imageTitle") String imageName,
            @RequestParam("imageAltName") String imageAltName,
            HttpServletRequest request) {
        try {
            String imageUrl = storeFileAndGetUrl(file); // Store the file and get its URL

            // Create an ImageInputDto object with imageUrl
            ImageInputDto inputDto = new ImageInputDto();
            inputDto.setImageFile(file);
            inputDto.setImageName(imageName);
            inputDto.setImageAltName(imageAltName);
            inputDto.setImageUrl(imageUrl); // Set the imageUrl

            // Add image
            ImageDto dto = imageService.addImage(inputDto);
            return ResponseEntity.created(new URI(dto.getImageUrl())).body(dto.getImageUrl());
        } catch (IOException | URISyntaxException | java.io.IOException e) {
            // Handle exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        }
    }


    // Method to store file and return its URL
    private String storeFileAndGetUrl(MultipartFile file) throws java.io.IOException {
        // Implement file storage logic here
        // Store the file to a location accessible via URL
        String fileName = file.getOriginalFilename();
        String fileUrl = environment.getProperty("base.url") + "/files/" + fileName;
        // Save the file and return its URL
        return fileUrl;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteImage(@PathVariable Long id) {
        imageService.deleteImage(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/images/test")
    public ResponseEntity<String> testImageEndpoint() {
        return ResponseEntity.ok("Test Image Endpoint successful");
    }

}
