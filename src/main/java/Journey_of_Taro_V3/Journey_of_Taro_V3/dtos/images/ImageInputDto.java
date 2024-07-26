package Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images;

import org.springframework.web.multipart.MultipartFile;

public class ImageInputDto {
    private String imageName;
    private String imageAltName;
    private MultipartFile imageFile;
    private String imageUrl;

    public ImageInputDto() {
    }

    public ImageInputDto(String imageName, String imageAltName, MultipartFile imageFile, String imageUrl) {
        this.imageName = imageName;
        this.imageAltName = imageAltName;
        this.imageFile = imageFile;
        this.imageUrl =imageUrl;
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

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
