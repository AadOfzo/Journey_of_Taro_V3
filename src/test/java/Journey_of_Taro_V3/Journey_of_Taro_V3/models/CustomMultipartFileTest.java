package Journey_of_Taro_V3.Journey_of_Taro_V3.models;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

public class CustomMultipartFileTest {
    @Test
    public void whenProvidingByteArray_thenMultipartFileCreated() throws IOException {
        byte[] inputArray = "Test String".getBytes();
        CustomMultipartFile customMultipartFile = new CustomMultipartFile(inputArray);
        Assertions.assertFalse(customMultipartFile.isEmpty());
        Assertions.assertArrayEquals(inputArray, customMultipartFile.getBytes());
        Assertions.assertEquals(inputArray.length,customMultipartFile.getSize());
    }

    @Test
    public void whenProvidingByteArray_thenMockMultipartFileCreated() throws IOException {
        byte[] inputArray = "Test String".getBytes();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("tempFileName",inputArray);
        Assertions.assertFalse(mockMultipartFile.isEmpty());
        Assertions.assertArrayEquals(inputArray, mockMultipartFile.getBytes());
        Assertions.assertEquals(inputArray.length,mockMultipartFile.getSize());
    }
}
