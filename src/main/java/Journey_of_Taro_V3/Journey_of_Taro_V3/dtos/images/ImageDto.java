package Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images;

public class ImageDto {
        private Long id;
        private String imageName;
        private String imageAltName;
        private String imageUrl;

        public ImageDto() {
        }

        public ImageDto(Long id, String imageName, String imageAltName, String imageUrl) {
            this.id = id;
            this.imageName = imageName;
            this.imageAltName = imageAltName;
            this.imageUrl = imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
