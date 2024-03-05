package Journey_of_Taro_V3.Journey_of_Taro_V3.models;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.io.File;

public class FileTest {

    @Test
    public void testFileMocking() {
        // Arrange
        File songFile = mock(File.class);
        File imageFile = mock(File.class);

        // Act
        // Perform any actions you need with the mocked files

        // Assert
        // Optionally, you can verify interactions with the mocked files using Mockito's verify() method
        verify(songFile, times(0)).getName(); // Example verification, adjust as needed
        verify(imageFile, times(0)).exists(); // Example verification, adjust as needed
    }
}
