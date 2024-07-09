package Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.images;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.CustomMultipartFile;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.Image;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    List<ImageDto> getAllImages();
    ImageDto addImage(ImageInputDto inputDto);
    ImageDto getImageById(Long id);
    ImageDto saveImage(ImageInputDto inputDto);
    void deleteImage(Long id);
    Image storeFile(MultipartFile imageFile, String imageUrl) throws IOException;
    Image storeFile(CustomMultipartFile imageFile, String imageUrl) throws IOException;
    Resource downloadImageFile(String imageName);
    Image getImageWithData(String imageName);
}
