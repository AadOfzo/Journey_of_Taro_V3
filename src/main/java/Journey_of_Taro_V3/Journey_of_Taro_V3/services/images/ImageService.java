package Journey_of_Taro_V3.Journey_of_Taro_V3.services.images;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageInputDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    List<ImageDto> getAllImages();
    ImageDto addImage(ImageInputDto inputDto);
    ImageDto getImageById(Long id);
    ImageDto saveImage(ImageInputDto inputDto);
    void deleteImage(Long id);
}

