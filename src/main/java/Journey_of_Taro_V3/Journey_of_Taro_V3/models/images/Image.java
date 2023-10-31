package Journey_of_Taro_V3.Journey_of_Taro_V3.models.images;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class Image {

    @Id
    @GeneratedValue
    private Long id;

    @Lob
    private byte[] data;

    private String imageName;
    private String imageAltName;

    public Image() {
    }

    public Image(Long id, byte[] data, String imageName, String imageAltName) {
        this.id = id;
        this.data = data;
        this.imageName = imageName;
        this.imageAltName = imageAltName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
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
