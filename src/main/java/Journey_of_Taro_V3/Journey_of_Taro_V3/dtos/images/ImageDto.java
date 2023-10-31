package Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images;

public class ImageDto {
    private Long id;
    private String imageName;
    private String imageAltName;

    public ImageDto() {
    }

    public ImageDto(Long id, String imageName, String imageAltName) {
        this.id = id;
        this.imageName = imageName;
        this.imageAltName = imageAltName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
