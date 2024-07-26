package Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images;

public class ImageDto {
        private Long imageId;
        private String imageName;
        private String imageAltName;
        private String imageUrl;
        private byte[] imageData;


    public ImageDto() {
        }

        public ImageDto(Long imageId, String imageName, String imageAltName, String imageUrl) {
            this.imageId = imageId;
            this.imageName = imageName;
            this.imageAltName = imageAltName;
            this.imageUrl = imageUrl;
        }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
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

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}
