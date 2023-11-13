package Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images;

import org.springframework.web.multipart.MultipartFile;

public class ImageInputDto {

    private String imageName;
    private String imageAltName;
    private MultipartFile file;

    public ImageInputDto() {
    }

    public ImageInputDto(String imageName, String imageAltName, MultipartFile file) {
        this.imageName = imageName;
        this.imageAltName = imageAltName;
        this.file = file;
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

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
