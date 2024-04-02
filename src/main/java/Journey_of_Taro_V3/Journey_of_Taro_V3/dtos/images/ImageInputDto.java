package Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images;

import org.springframework.web.multipart.MultipartFile;

public class ImageInputDto {
    private MultipartFile imageFile;
    private String imageName;
    private String imageAltName;
    private String imageUrl;

    public ImageInputDto() {
    }

    public ImageInputDto(MultipartFile imageFile, String imageName, String imageAltName, String imageUrl) {
        this.imageFile = imageFile;
        this.imageName = imageName;
        this.imageAltName = imageAltName;
        this.imageUrl = imageUrl;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
