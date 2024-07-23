package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.images;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.RecordNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.CustomMultipartFile;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.Image;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.images.ImageServiceImpl;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.users.UserService;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/images")
public class ImageController {
    private final UserService userService;
    private final ImageServiceImpl imageService;
    public Environment environment;

    @Autowired
    public ImageController(UserService userService, ImageServiceImpl imageService) {
        this.userService = userService;
        this.imageService = imageService;
    }

    @GetMapping
    public ResponseEntity<List<ImageDto>> getAllImages() {
        List<ImageDto> images = imageService.getAllImages();
        return ResponseEntity.ok(images);
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

    @GetMapping("/{imageName:.+}")
    public ResponseEntity<Resource> downloadImage(@PathVariable String imageName) {
        Resource resource = imageService.downloadImageFile(imageName);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/image/{imageName}")
    public ResponseEntity<byte[]> getImageFile(@PathVariable("imageName") String imageName){

        Image image = imageService.getImageWithData(imageName);

        MediaType mediaType;

        try {
            mediaType = MediaType.IMAGE_JPEG;
        } catch (InvalidMediaTypeException ignore){
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        }

        return ResponseEntity
                .ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + image.getImageName())
                .body(image.getImageData());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addImage(
            @RequestParam("file") CustomMultipartFile file,
            @RequestParam("imageName") String imageName,
            @RequestParam("imageAltName") String imageAltName,
            HttpServletRequest request) {
        try {
            String imageUrl = storeFileAndGetUrl(file); // Store the file and get its URL

            // Create an ImageInputDto object with imageUrl
            ImageInputDto inputDto = new ImageInputDto();
            inputDto.setImageFile(file);
            inputDto.setImageName(imageName);
            inputDto.setImageAltName(imageAltName);

            // Add image
            ImageDto dto = imageService.addImage(inputDto);

            Long imageId = dto.getImageId();

            System.out.println("Added " + imageName + " to the database with id: " + imageId);
            return ResponseEntity.created(new URI(dto.getImageUrl())).body(dto.getImageUrl());
        } catch (IOException | URISyntaxException | java.io.IOException e) {
            // Handle exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        }
    }

    // Method to store file and return URL moet waarschijnlijk in de ImageService.
    private String storeFileAndGetUrl(MultipartFile file) throws java.io.IOException {

        String fileName = file.getOriginalFilename();
        String fileUrl = environment.getProperty("base.url") + "/files/" + fileName;

        return fileUrl;
    }

    // Delete Mapping
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteImage(@PathVariable Long id) {
        imageService.deleteImage(id);
        return ResponseEntity.noContent().build();
    }

    // Test
    @GetMapping("/images/test")
    public ResponseEntity<String> testImageEndpoint() {
        return ResponseEntity.ok("Test Image Endpoint successful");
    }

    }
