package Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.images;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.RecordNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.CustomMultipartFile;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.Image;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.images.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    private final Path fileStoragePath;
    private final String fileStorageLocation;
    private final ImageRepository imageRepository;

    @Autowired
    public ImageServiceImpl(@Value("uploads/images") String fileStorageLocation, ImageRepository imageRepository) throws IOException {
        fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();
        this.fileStorageLocation = fileStorageLocation;
        this.imageRepository = imageRepository;

        Files.createDirectories(fileStoragePath);
    }

    @Override
    public ImageDto getImageById(Long id) {
        return imageRepository.findById(id)
                .map(this::transferToImageDto)
                .orElseThrow(() -> new RecordNotFoundException("No Images found with id: " + id));
    }

    @Override
    public List<ImageDto> getAllImages() {
        List<Image> images = imageRepository.findAll();
        return transferImageListToDtoList(images);
    }

    @Override
    public ImageDto addImage(ImageInputDto inputDto) {
        Image image = transferToImage(inputDto);

        // Set imageUrl
        String imageUrl = "/uploads/images/" + inputDto.getImageFile().getOriginalFilename();
        image.setImageUrl(imageUrl);

        image = imageRepository.save(image);
        return transferToImageDto(image);
    }

    @Override
    public ImageDto saveImage(ImageInputDto inputDto) {
        Image image = transferToImage(inputDto);

        image = imageRepository.save(image);
        return transferToImageDto(image);
    }

    @Override
    public Image storeFile(MultipartFile imageFile, String imageUrl) throws IOException {
        String originalFileName = imageFile.getOriginalFilename();
        byte[] imageData = imageFile.getBytes();
        String contentType = imageFile.getContentType();

        CustomMultipartFile customFile = new CustomMultipartFile(originalFileName, contentType, imageData);
        Image image = new Image(customFile, imageUrl, imageData, originalFileName, contentType);

        return imageRepository.save(image);
    }

    @Override
    public Image storeFile(CustomMultipartFile imageFile, String imageUrl) throws IOException {
        String originalFileName = imageFile.getOriginalFilename();
        byte[] imageData = imageFile.getBytes();
        String contentType = imageFile.getContentType();

        Image image = new Image(imageFile, imageUrl, imageData, originalFileName, contentType);

        return imageRepository.save(image);
    }

    private List<ImageDto> transferImageListToDtoList(List<Image> images) {
        List<ImageDto> imageDtoList = new ArrayList<>();
        for (Image image : images) {
            imageDtoList.add(transferToImageDto(image));
        }
        return imageDtoList;
    }

    private ImageDto transferToImageDto(Image image) {
        ImageDto dto = new ImageDto();
        dto.setId(image.getId());
        dto.setImageName(image.getImageName());
        dto.setImageAltName(image.getImageAltName());
        dto.setImageUrl(image.getImageUrl());
        dto.setImageData(image.getImageData());
        return dto;
    }

    private Image transferToImage(ImageInputDto dto) {
        Image image = new Image();
        image.setFileName(dto.getImageFile().getOriginalFilename());
        image.setImageName(dto.getImageName());
        image.setImageAltName(dto.getImageAltName());
        image.setFileSize(dto.getImageFile().getSize());
        image.setUploadTime(LocalDateTime.now());
        try {
            image.setImageData(dto.getImageFile().getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Error creating image entity", e);
        }

        return image;
    }

    @Override
    public Resource downloadImageFile(String imageName) {
        Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(imageName);

        try {
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("The file doesn't exist or is not readable.");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Issue in reading the file.", e);
        }
    }

    @Override
    public Image getImageWithData(String imageName) {
        Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(imageName);

        try {
            byte[] imageData = Files.readAllBytes(path);
            Image image = new Image();
            image.setImageData(imageData);
            return image;
        } catch (IOException e) {
            throw new RuntimeException("Issue reading the file", e);
        }
    }

    @Override
    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }
}
