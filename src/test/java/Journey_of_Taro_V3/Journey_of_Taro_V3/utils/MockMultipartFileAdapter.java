package Journey_of_Taro_V3.Journey_of_Taro_V3.utils;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.CustomMultipartFile;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

// Convert MockMultipartFile to CustomMultipartFile
public class MockMultipartFileAdapter extends CustomMultipartFile {

    public MockMultipartFileAdapter(MockMultipartFile mockMultipartFile) throws IOException {
        super(mockMultipartFile.getOriginalFilename(), mockMultipartFile.getContentType(), mockMultipartFile.getBytes());
    }
}
