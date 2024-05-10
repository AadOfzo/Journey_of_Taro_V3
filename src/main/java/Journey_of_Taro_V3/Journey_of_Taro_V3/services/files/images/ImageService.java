package Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.images;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.Image;
import org.springframework.core.io.Resource;

import java.util.List;

public interface ImageService {
    List<ImageDto> getAllImages();
    ImageDto addImage(ImageInputDto inputDto);
    ImageDto getImageById(Long id);
    ImageDto saveImage(ImageInputDto inputDto);
    void deleteImage(Long id);

    Image getImageWithData(String imageName);
}

