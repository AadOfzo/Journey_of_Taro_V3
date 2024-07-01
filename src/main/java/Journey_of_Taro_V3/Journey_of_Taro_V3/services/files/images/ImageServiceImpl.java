package Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.images;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.RecordNotFoundException;
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
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    private final Path fileStoragePath;
    private final String fileStorageLocation;
    private final ImageRepository imageRepository;

    @Autowired
    public ImageServiceImpl(@Value("${file.storage.location:uploads/images}") String fileStorageLocation, ImageRepository imageRepository) throws IOException {
        this.fileStorageLocation = fileStorageLocation;
        this.fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();
        this.imageRepository = imageRepository;

        Files.createDirectories(fileStoragePath);
    }

    @Override
    public ImageDto getImageById(Long id) {
        Optional<Image> optionalImage = imageRepository.findById(id);
        if (optionalImage.isPresent()) {
            Image image = optionalImage.get();
            return transferToImageDto(image);
        } else {
            throw new RecordNotFoundException("No Images found with id: " + id);
        }
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

    public String storeImageFile(MultipartFile file) throws IOException {
        String fileName = Paths.get(file.getOriginalFilename()).getFileName().toString();
        Path targetLocation = this.fileStoragePath.resolve(fileName);

        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        return targetLocation.toString();
    }


    public Resource loadAsResource(String filename) {
        try {
            Path filePath = this.fileStoragePath.resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("The file doesn't exist or not readable.");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Issue in reading the file.", e);
        }
    }

    @Override
    public ImageDto saveImage(ImageInputDto inputDto) {
        Image image = transferToImage(inputDto);
        image = imageRepository.save(image);
        return transferToImageDto(image);
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
    public Image getImageWithData(String imageName) {
        Optional<Image> optionalImage = imageRepository.findByFileName(imageName);
        if (optionalImage.isPresent()) {
            return optionalImage.get();
        } else {
            throw new RecordNotFoundException("Image not found with name: " + imageName);
        }
    }

    public Resource downloadImageFile(String imageName) {
        Path path = fileStoragePath.resolve(imageName);
        try {
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("The file doesn't exist or not readable.");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Issue in reading the file.", e);
        }
    }

    @Override
    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }
}
