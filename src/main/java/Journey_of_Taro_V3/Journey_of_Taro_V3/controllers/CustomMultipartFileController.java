package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.FileInfo;
import java.io.IOException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class CustomMultipartFileController {

    @PostMapping(value = "/fileUpload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public FileInfo fileUploadController(@RequestParam("file") MultipartFile userFile) {

        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileName(userFile.getOriginalFilename());
        fileInfo.setContentType(userFile.getContentType());
        fileInfo.setEmpty(userFile.isEmpty());
        fileInfo.setReadable(userFile.getResource().isReadable());

        try {
            String filePath = "";
            String fileName = userFile.getOriginalFilename();
            String entireFilePath = filePath + fileName;
            File f = new File(entireFilePath);

            if (f.exists()) {
                String newFileName = entireFilePath.replace(".", "_" + fileInfo.getCurrentTimeStamp() + ".");
                File bkpFile = new File(newFileName);
                if (f.renameTo(bkpFile)) {
                    System.out.println("File successfully renamed.");
                } else {
                    System.out.println("Failed to rename file.");
                }
            }

            fileInfo.setFileData(userFile.getBytes());

            userFile.transferTo(f);
            fileInfo.setFileUploadStatus("Success!");
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
            fileInfo.setFileUploadStatus("Failed due to IOException: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            fileInfo.setFileUploadStatus("Failed due to " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }

        return fileInfo;
    }
}
