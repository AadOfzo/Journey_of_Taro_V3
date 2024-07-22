package Journey_of_Taro_V3.Journey_of_Taro_V3.services;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.RecordNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.Image;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.images.ImageRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.images.UserImageRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.images.ImageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ImageServiceImplTest {

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private UserImageRepository userImageRepository;

    @InjectMocks
    private ImageServiceImpl imageService;

    private Path fileStoragePath;

    @BeforeEach
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);

        // Initialize file storage path for testing
        fileStoragePath = Paths.get("uploads/images").toAbsolutePath().normalize();
        Files.createDirectories(fileStoragePath);

        // Manually set the file storage path in the service instance
        imageService = new ImageServiceImpl(fileStoragePath.toString(), imageRepository, userImageRepository);
    }

    @Test
    public void testStoreFile() throws IOException {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("file", "image1.jpg", "image/jpeg", "test image data".getBytes());
        String expectedFileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path filePath = fileStoragePath.resolve(expectedFileName);

        // Act
        String result = imageService.storeFile(file);

        // Assert
        assertEquals(expectedFileName, result);
        assertTrue(Files.exists(filePath));
    }

    @Test
    public void testGetImageById() {
        // Arrange
        Image image = new Image();
        image.setImageId(1L);
        image.setImageName("Image 1");
        when(imageRepository.findById(1L)).thenReturn(Optional.of(image));

        // Act
        ImageDto result = imageService.getImageById(1L);

        // Assert
        assertEquals("Image 1", result.getImageName());
    }

    @Test
    public void testGetImageById_NotFound() {
        // Arrange
        when(imageRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RecordNotFoundException.class, () -> imageService.getImageById(1L));
    }

    @Test
    public void testGetAllImages() {
        // Arrange
        Image image1 = new Image();
        image1.setImageId(1L);
        image1.setImageName("Image 1");
        Image image2 = new Image();
        image2.setImageId(2L);
        image2.setImageName("Image 2");
        when(imageRepository.findAll()).thenReturn(Arrays.asList(image1, image2));

        // Act
        List<ImageDto> result = imageService.getAllImages();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Image 1", result.get(0).getImageName());
        assertEquals("Image 2", result.get(1).getImageName());
    }

    @Test
    public void testAddImage() throws IOException {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("file", "image1.jpg", "image/jpeg", "test image data".getBytes());
        ImageInputDto inputDto = new ImageInputDto("Image 1", "alt1", file);
        Image image = new Image();
        image.setImageId(1L);
        image.setImageName("Image 1");
        when(imageRepository.save(any(Image.class))).thenReturn(image);

        // Act
        ImageDto result = imageService.addImage(inputDto);

        // Assert
        assertEquals("Image 1", result.getImageName());
    }

    @Test
    public void testSaveImage() throws IOException {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("file", "image1.jpg", "image/jpeg", "test image data".getBytes());
        ImageInputDto inputDto = new ImageInputDto("Image 1", "alt1", file);
        Image image = new Image();
        image.setImageId(1L);
        image.setImageName("Image 1");
        image.setImageUrl("/uploads/images/" + file.getOriginalFilename());
        when(imageRepository.save(any(Image.class))).thenReturn(image);

        // Act
        ImageDto result = imageService.saveImage(inputDto);

        // Assert
        assertEquals("Image 1", result.getImageName());
        assertEquals("/uploads/images/" + file.getOriginalFilename(), result.getImageUrl());
    }

    @Test
    public void testDownloadImageFile() throws MalformedURLException {
        // Arrange
        String imageName = "image1.jpg";
        Path path = fileStoragePath.resolve(imageName).normalize();
        try {
            Files.write(path, "test image data".getBytes());
            Resource resource = new UrlResource(path.toUri());
            when(imageService.downloadImageFile(imageName)).thenReturn(resource);

            // Act
            Resource result = imageService.downloadImageFile(imageName);

            // Assert
            assertNotNull(result);
            assertTrue(result.exists());
        } catch (IOException e) {
            fail("IOException should not be thrown during file operations");
        }
    }

    @Test
    public void testDownloadImageFile_FileNotFound() {
        // Arrange
        String imageName = "nonexistent.jpg";
        when(imageService.downloadImageFile(imageName)).thenThrow(new RuntimeException("File not found"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> imageService.downloadImageFile(imageName));
    }

    @Test
    public void testGetImageWithData() throws IOException {
        // Arrange
        String imageName = "image1.jpg";
        Path path = fileStoragePath.resolve(imageName).normalize();
        try {
            Files.write(path, "test image data".getBytes());

            // Act
            Image result = imageService.getImageWithData(imageName);

            // Assert
            assertEquals(imageName, result.getImageName());
            assertArrayEquals("test image data".getBytes(), result.getImageData());
        } catch (IOException e) {
            fail("IOException should not be thrown during file operations");
        }
    }

    @Test
    public void testDeleteImage() {
        // Arrange
        Long imageId = 1L;

        // Act
        imageService.deleteImage(imageId);

        // Assert
        verify(imageRepository, times(1)).deleteById(imageId);
    }
}
