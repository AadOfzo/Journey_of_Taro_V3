package Journey_of_Taro_V3.Journey_of_Taro_V3.models.images;

import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "Images")
public class Image {

    @Id
    @GeneratedValue
    private Long id;

    @Lob
    private byte[] imageData;

    private String imageName;
    private String imageAltName;

    public Image() {
    }

    public Image(String imageName, String imageAltName, MultipartFile imageFile) {
        this.imageName = imageName;
        this.imageAltName = imageAltName;
        try {
            this.imageData = imageFile.getBytes();
        } catch (Exception e) {
            e.printStackTrace();
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
        if (imageName == null || imageName.trim().isEmpty()) {

        }
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
}
