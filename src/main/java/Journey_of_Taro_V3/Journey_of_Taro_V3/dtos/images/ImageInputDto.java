package Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public class ImageInputDto {

    @NotNull(message = "Image data is required")
    private MultipartFile data;

    @NotBlank(message = "Image name is required")
    private String imageName;

    public ImageInputDto(MultipartFile data, String imageName) {
        this.data = data;
        this.imageName = imageName;
    }

    public MultipartFile getData() {
        return data;
    }

    public void setData(MultipartFile data) {
        this.data = data;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
