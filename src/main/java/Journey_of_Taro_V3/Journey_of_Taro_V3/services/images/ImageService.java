package Journey_of_Taro_V3.Journey_of_Taro_V3.services.images;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.BadRequestException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.RecordNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.Image;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.images.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public List<ImageDto> getAllImages() {
        List<Image> images = imageRepository.findAll();
        return images.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public ImageDto getImageById(Long id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No image found with the ID: " + id));
        return convertToDto(image);
    }

    public ImageDto addImage(ImageInputDto inputDto) {
        try {
            Image image = convertToEntity(inputDto);
            imageRepository.save(image);
            return convertToDto(image);
        } catch (IOException e) {
            // Exception Logger
            e.printStackTrace(); // log

            // Rethrow Exception return response
            throw new BadRequestException("Failed to add image. Check your request data.");
        }

    }

    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }

    private ImageDto convertToDto(Image image) {
        return new ImageDto(image.getId(), image.getImageName(), image.getImageAltName());
    }

    private Image convertToEntity(ImageInputDto inputDto) throws IOException {
        Image image = new Image();
        image.setImageName(inputDto.getImageName());
        image.setImageAltName(inputDto.getImageAltName());

        // Multipart conversie
        byte[] imageData = inputDto.getImageFile().getBytes();
        image.setImageData(imageData);

        return image;
    }
}
