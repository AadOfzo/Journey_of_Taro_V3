package Journey_of_Taro_V3.Journey_of_Taro_V3.models.images;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.CustomMultipartFile;
import jakarta.persistence.*;

import java.io.IOException;
import java.time.LocalDateTime;

@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue
    private Long id;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] imageData;
    @Column (name = "imagename")
    private String imageName;
    private String imageAltName;
    private String fileName;
    private Long fileSize;
    private LocalDateTime uploadTime;
    private String imageUrl;

    public Image() {
    }

    public Image(CustomMultipartFile imageFile, String imageUrl) {
        this.imageName = imageName;
        this.imageAltName = imageAltName;
        this.imageUrl = imageUrl;
        try {
            this.imageData = imageFile.getBytes();
            this.fileName = imageFile.getOriginalFilename();
            this.fileSize = imageFile.getSize();
            this.uploadTime = LocalDateTime.now();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception appropriately
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageAltName() {
        return imageAltName;
    }

    public void setImageAltName(String imageAltName) {
        this.imageAltName = imageAltName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
