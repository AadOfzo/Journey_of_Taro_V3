package Journey_of_Taro_V3.Journey_of_Taro_V3.models.images;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.CustomMultipartFile;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.SongCollection;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue
    private Long imageId;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] imageData;
    @Column(name = "imagename")
    private String imageName;
    private String imageAltName;
    private String fileName;
    private Long fileSize;
    private LocalDateTime uploadTime;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "collection_id")
    private SongCollection songCollection;

    public Image() {
    }

    public Image(Long imageId, String fileName, String imageName, String imageAltName, String imageUrl, byte[] imageData, LocalDateTime uploadTime, Long fileSize) {
        this.imageId = imageId;
        this.fileName = fileName;
        this.imageName = imageName;
        this.imageAltName = imageAltName;
        this.imageUrl = imageUrl;
        this.imageData = imageData;
        this.uploadTime = uploadTime;
        this.fileSize = fileSize;
    }

    public Image(byte[] imageData, String imageUrl, String imageName, String imageAltName, String fileName, Long fileSize) {
        if (imageData == null || imageData.length == 0) {
            throw new IllegalArgumentException("imageData can't be null or empty");
        }
        this.imageData = imageData;
        this.imageUrl = imageUrl;
        this.imageName = imageName;
        this.imageAltName = imageAltName;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.uploadTime = LocalDateTime.now();
    }

//    public Image(CustomMultipartFile imageFile, String imageUrl, byte[] imageData, String imageName, String imageAltName) {
//        this.imageData = imageData;
//        this.imageUrl = imageUrl;
//        this.imageName = imageName;
//        this.imageAltName = imageAltName;
//        this.fileName = imageFile.getOriginalFilename();
//        this.fileSize = imageFile.getSize();
//        this.uploadTime = LocalDateTime.now();
//    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
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

    public SongCollection getSongCollection() {
        return songCollection;
    }

    public void setSongCollection(SongCollection songCollection) {
        this.songCollection = songCollection;
    }
}
