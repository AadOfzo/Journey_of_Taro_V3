package Journey_of_Taro_V3.Journey_of_Taro_V3.services.images;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.RecordNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.Image;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.images.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public ImageDto getImageById(Long id) {

        if (imageRepository.findById(id).isPresent()){
            Image image = imageRepository.findById(id).get();
            ImageDto dto = transferToImageDto(image);

            return transferToImageDto(image);
        } else {
            throw new RecordNotFoundException("No Images found" + (id));
        }
    }

    @Override
    public List<ImageDto> getAllImages() {
        List<Image> images = imageRepository.findAll();
        return transferImageListToDtoList(images);
    }

    private List<ImageDto> transferImageListToDtoList(List<Image> images) {
        List<ImageDto> imageDtoList = new ArrayList<>();
        for (Image image : images) {
            imageDtoList.add(transferToImageDto(image)); // Add the transferred imageDTO to the list
        }
        return imageDtoList;
    }

    @Override
    public ImageDto addImage(ImageInputDto inputDto) {
        Image image = transferToImage(inputDto);
        image = imageRepository.save(image); // Save the image and assign the returned value
        return transferToImageDto(image);
    }

    private ImageDto transferToImageDto(Image image) {
        ImageDto dto = new ImageDto();
        // Set properties of ImageDto from Image entity
        dto.setId(image.getId());
        dto.setImageName(image.getImageName());
        dto.setImageAltName(image.getImageAltName());
        // Set other properties as needed
        return dto;
    }

    private Image transferToImage(ImageInputDto dto) {
        Image image = new Image();
        // Set properties of Image entity from ImageInputDto
        image.setFileName(dto.getImageFile().getOriginalFilename());
        image.setImageName(dto.getImageName()); // Set imageName from ImageInputDto
        image.setImageAltName(dto.getImageAltName()); // Set imageAltName from ImageInputDto
        image.setFileSize(dto.getImageFile().getSize()); // Set fileSize from ImageInputDto
        image.setUploadTime(LocalDateTime.now()); // Set uploadTime to current time
        try {
            image.setImageData(dto.getImageFile().getBytes()); // Set imageData from ImageInputDto
        } catch (IOException e) {
            // Handle IOException appropriately
            e.printStackTrace();
        }
        // Set other properties as needed
        return image;
    }

    @Override
    public ImageDto saveImage(ImageInputDto inputDto) {
        Image image = transferToImage(inputDto);
        image = imageRepository.save(image); // Save the image and assign the returned value
        return transferToImageDto(image);
    }

    @Override
    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }

}
