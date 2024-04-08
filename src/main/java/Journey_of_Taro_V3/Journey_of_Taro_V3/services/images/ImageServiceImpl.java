package Journey_of_Taro_V3.Journey_of_Taro_V3.services.images;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.RecordNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.CustomMultipartFile;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.Image;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.images.ImageRepository;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

@Service
public class ImageServiceImpl implements ImageService {

    //    private final FileUploadRepository uploadRepository;

    private final ImageRepository imageRepository;

//    public ImageServiceImpl(Path fileStoragePath, String fileStorageLocation, FileUploadRepository uploadRepository, ImageRepository imageRepository) {
//        this.fileStoragePath = fileStoragePath;
//        this.fileStorageLocation = fileStorageLocation;
//        this.uploadRepository = uploadRepository;
//        this.imageRepository = imageRepository;
//    }

    public ImageServiceImpl(@Value("uploads") String fileStorageLocation, ImageRepository imageRepository) throws IOException{
        Path fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();
        this.imageRepository = imageRepository;

        Files.createDirectories(fileStoragePath);
    }

    @Override
    public ImageDto getImageById(Long id) {
        if (imageRepository.findById(id).isPresent()) {
            Image image = imageRepository.findById(id).get();
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

        // Set imageUrl based on the file name and storage location
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
        dto.setId(image.getId());
        dto.setImageName(image.getImageName());
        dto.setImageAltName(image.getImageAltName());
        dto.setImageUrl(image.getImageUrl());
        // Set other properties as needed
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
            e.printStackTrace();
        }
        // Set other properties as needed
        return image;
    }

    // todo Image opslag en download: Misschien is de saveImage methode ook te gebruiken
//    public String storeImageFile(CustomMultipartFile imageFile) throws IOException{
//
//        String imageName = StringUtils.cleanPath(Objects.requireNonNull(imageFile.getOriginalFilename()));
//        Path filePath = Paths.get(fileStoragePath + "\\" + imageName);
//
//        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//
//        imageRepository.save(new Image(imageName));
//        return imageName;
//    }
//
//    public Resource downloadImageFile(String fileName) {
//
//        Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(fileName);
//
//        Resource resource;
//
//        try {
//            resource = new UrlResource(path.toUri());
//        } catch (MalformedURLException e) {
//            throw new RuntimeException("Issue in reading the file.");
//        }
//
//        if(resource.exists()&& resource.isReadable()) {
//            return resource;
//        } else {
//            throw new RuntimeException("The file doesn't exist or not readable.");
//        }
//    }


}
