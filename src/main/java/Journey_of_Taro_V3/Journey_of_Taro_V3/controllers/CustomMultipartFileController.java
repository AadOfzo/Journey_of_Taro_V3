package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.FileInfo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
@RestController
public class CustomMultipartFileController {

    // localhost:8080/fileUpload Postman Post request checks file type!
    @PostMapping(value = "/fileUpload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public FileInfo fileUploadController(MultipartFile file) throws IOException {
        // Check if the file is empty
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        // Determine the file type
        String fileType = determineFileType(file.getContentType());

        // Check if the file type is null
        if (fileType == null) {
            throw new IllegalArgumentException("Unsupported file type");
        }

        // Get the original file name
        String originalFilename = file.getOriginalFilename();

        // Create a FileInfo object
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileName(originalFilename);
        fileInfo.setFileSize(file.getSize());
        fileInfo.setFileType(fileType);
        fileInfo.setUploadTime(LocalDateTime.now());

        // Print file information
        System.out.println("Filename: " + fileInfo.getFileName());
        System.out.println("File type: " + fileInfo.getFileType());
        System.out.println("File size: " + fileInfo.getFileSize());

        return fileInfo;
    }

    // Method to determine file type based on content type
    public String determineFileType(String contentType) {
        if (contentType != null && contentType.startsWith("image")) {
            return "Image";
        } else if (contentType != null && contentType.startsWith("audio")) {
            return "Audio";
        } else {
            return "Unknown";
        }
    }
}