package Journey_of_Taro_V3.Journey_of_Taro_V3.models;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.Image;
import org.junit.Test;

public class ImageTest {

    @Test
    public void testImageAttributes() {
        // Creating a test image file
        byte[] imageData = new byte[1024]; // Dummy image data
        String imageName = "Test Image";
        String imageAltName = "Alternate Test Image";
        CustomMultipartFile imageFile = new CustomMultipartFile("test_image.jpg", "image/jpeg", imageData);

        System.out.println("Image file created");

        // Creating a test image entity
        Image image = new Image(imageName, imageAltName, imageFile);

        // Printing attributes of the image entity
        System.out.println("Image Name: " + image.getImageName());
        System.out.println("Alternate Image Name: " + image.getImageAltName());
        System.out.println("File Name: " + image.getFileName());
        System.out.println("File Size: " + image.getFileSize());
        System.out.println("Upload Time: " + image.getUploadTime());
    }


}
