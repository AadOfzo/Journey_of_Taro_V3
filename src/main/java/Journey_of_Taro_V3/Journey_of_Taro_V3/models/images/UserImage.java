package Journey_of_Taro_V3.Journey_of_Taro_V3.models.images;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UserImage {

    @Id
    private String fileName;

    public UserImage(String fileName) {
        this.fileName = fileName;
    }

    public UserImage() {
    }

    public String getFileName() {
        return fileName;
    }
}
