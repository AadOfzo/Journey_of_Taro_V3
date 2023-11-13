package Journey_of_Taro_V3.Journey_of_Taro_V3.models.images;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import org.springframework.web.multipart.MultipartFile;

@Entity
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

    public Image(String imageName, String imageAltName, MultipartFile file) {
        this.imageName = imageName;
        this.imageAltName = imageAltName;
        try {
            this.imageData = file.getBytes();
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
