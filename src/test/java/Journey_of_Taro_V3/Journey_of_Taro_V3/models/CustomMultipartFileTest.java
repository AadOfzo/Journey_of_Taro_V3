package Journey_of_Taro_V3.Journey_of_Taro_V3.models;

import Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.CustomMultipartFileController;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.Assert.*;

public class CustomMultipartFileTest {

    @Test
    public void whenProvidingByteArray_thenMultipartFileCreated() throws IOException {
        byte[] inputArray = "Test String".getBytes();
        CustomMultipartFile customMultipartFile = new CustomMultipartFile(inputArray, "", "", "");
        Assertions.assertFalse(customMultipartFile.isEmpty());
        Assertions.assertArrayEquals(inputArray, customMultipartFile.getBytes());
        Assertions.assertEquals(inputArray.length, customMultipartFile.getSize());
    }

    @Test
    public void whenProvidingByteArray_thenMockMultipartFileCreated() throws IOException {
        byte[] inputArray = "Test String".getBytes();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("tempFileName", inputArray);
        Assertions.assertFalse(mockMultipartFile.isEmpty());
        Assertions.assertArrayEquals(inputArray, mockMultipartFile.getBytes());
        Assertions.assertEquals(inputArray.length, mockMultipartFile.getSize());
    }

    @Test
    public void testDetermineFileType() {
        // Arrange
        byte[] fileContent = "test content".getBytes();
        MultipartFile multipartFile = new MockMultipartFile("test.txt", fileContent);
        CustomMultipartFileController controller = new CustomMultipartFileController();

        // Act
        String result = controller.determineFileType(multipartFile.getContentType());

        // Assert
        assertNotNull(result);
        System.out.println("File type: " + result);
    }
}



