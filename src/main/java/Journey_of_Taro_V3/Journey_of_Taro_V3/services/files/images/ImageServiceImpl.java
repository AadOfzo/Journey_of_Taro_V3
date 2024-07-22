package Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.images;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.RecordNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.Image;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.UserImage;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.images.ImageRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.images.UserImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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
import java.util.Objects;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {
    private final Path fileStoragePath;
    private final String fileStorageLocation;
    private final ImageRepository imageRepository;
    private final UserImageRepository userImageRepository;

    @Autowired
    public ImageServiceImpl(@Value("${my.upload.location}") String fileStorageLocation, ImageRepository imageRepository, UserImageRepository userImageRepository) throws IOException {
        fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();
        this.fileStorageLocation = fileStorageLocation;
        this.imageRepository = imageRepository;
        this.userImageRepository = userImageRepository;

        Files.createDirectories(fileStoragePath);
    }

    @Override
    public List<ImageDto> getAllImages() {
        List<Image> images = imageRepository.findAll();
        return transferImageListToDtoList(images);
    }

    @Override
    public ImageDto getImageById(Long id) {
        return imageRepository.findById(id)
                .map(this::transferToImageDto)
                .orElseThrow(() -> new RecordNotFoundException("No Images found with id: " + id));
    }

    @Override
    public ImageDto addImage(ImageInputDto inputDto) {
        Image image = transferToImage(inputDto);
        image = imageRepository.save(image);
        return transferToImageDto(image);
    }

    @Override
    public ImageDto saveImage(ImageInputDto inputDto) {
        Image image = transferToImage(inputDto);
        String imageUrl = "/uploads/images/" + inputDto.getImageFile().getOriginalFilename();
        image.setImageUrl(imageUrl);
        image = imageRepository.save(image);
        return transferToImageDto(image);
    }

    @Override
    public String storeFile(MultipartFile file) throws IOException {
        String imageName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Path filePath = Paths.get(fileStoragePath + "\\" + imageName);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        userImageRepository.save(new UserImage(imageName));
        return imageName;
    }

//    @Override
//    public Image storeFile(MultipartFile file) throws IOException {
//        if (file.isEmpty()) {
//            throw new IllegalArgumentException("Cannot store empty file");
//        }
//
//        String imageName = UUID.randomUUID() + "_" + file.getOriginalFilename();
//        Path targetLocation = this.fileStoragePath.resolve(imageName);
//
//        // Ensure target directory exists
//        if (!Files.exists(this.fileStoragePath)) {
//            Files.createDirectories(this.fileStoragePath);
//        }
//
//        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
//
//        Image image = new Image();
//        image.setImageName(imageName);
//        image.setImageUrl(imageUrl);
//        image.setImageData(file.getBytes());
//        image.setFileName(fileName);
//        image.setFileSize(file.getSize());
//        image.setUploadTime(LocalDateTime.now());
//
//        return imageRepository.save(image);
//    }


    @Override
    public Resource downloadImageFile(String imageName) {
        Path path = this.fileStoragePath.resolve(imageName).normalize();

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
        Path path = this.fileStoragePath.resolve(imageName).normalize();

        try {
            byte[] imageData = Files.readAllBytes(path);
            Image image = new Image();
            image.setImageName(imageName);
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

    private List<ImageDto> transferImageListToDtoList(List<Image> images) {
        List<ImageDto> imageDtoList = new ArrayList<>();
        for (Image image : images) {
            imageDtoList.add(transferToImageDto(image));
        }
        return imageDtoList;
    }

    private ImageDto transferToImageDto(Image image) {
        ImageDto dto = new ImageDto();
        dto.setImageId(image.getImageId());
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
}
