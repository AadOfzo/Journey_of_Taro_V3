package Journey_of_Taro_V3.Journey_of_Taro_V3.models.images;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UserImage {


    /* Het opgeslagen bestand staat niet in deze klasse opgeslagen.
       Deze klasse heeft enkel een verwijzing naar de naam van het bestand.
       We weten waar het bestand staat opgeslagen,
       dus met de naam kunnen we naar het bestand verwijzen als "./uploads/{fileName}"
    */
    @Id
    @Column(nullable = false)
    private String imageName;

    public UserImage(String imageName) {
        this.imageName = imageName;
    }

    public UserImage() {
    }

    public String getUserImage() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public String toString() {
        return "UserImage{" +
                "imageName='" + imageName + '\'' +
                '}';
    }

}
