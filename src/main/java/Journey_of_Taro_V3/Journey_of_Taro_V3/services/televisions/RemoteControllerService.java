package Journey_of_Taro_V3.Journey_of_Taro_V3.services.televisions;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.televisions.RemoteControllerDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.RecordNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.televisions.RemoteController;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.televisions.RemoteControllerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Deze klasse bevat de Service methodes van RemoteControllerController

@Service
public class RemoteControllerService  {

    private final RemoteControllerRepository remoteControllerRepository;


    public RemoteControllerService(RemoteControllerRepository remoteControllerRepository) {
        this.remoteControllerRepository = remoteControllerRepository;
    }

    public List<RemoteControllerDto> getAllRemoteControllers() {
        List<RemoteControllerDto> dtos = new ArrayList<>();
        List<RemoteController> remoteControllers = remoteControllerRepository.findAll();
        for (RemoteController rc : remoteControllers) {
            dtos.add(transferToDto(rc));
        }
        return dtos;
    }

    public RemoteControllerDto getRemoteController(long id) {
        Optional<RemoteController> remoteController = remoteControllerRepository.findById(id);
        if(remoteController.isPresent()) {
            return transferToDto(remoteController.get());
        } else {
            throw new RecordNotFoundException("No remotecontroller found");
        }
    }

    public RemoteControllerDto addRemoteController(RemoteControllerDto remoteControllerDto) {
        RemoteController rc =  transferToRemoteController(remoteControllerDto);
        remoteControllerRepository.save(rc);
        return remoteControllerDto;
    }

    public Boolean deleteRemoteController(Long id) {
        if(remoteControllerRepository.existsById(id)) {
            remoteControllerRepository.deleteById(id);
            //return true als het deleten is geslaagd
            return true;
        }
        //return false als het id niet in de DB staat en het deleten dus niet is geslaagd.
        return false;
    }

    public void updateRemoteController(Long id, RemoteControllerDto remoteControllerDto) {
        if(!remoteControllerRepository.existsById(id)) {
            throw new RecordNotFoundException("No remotecontroller found");
        }
        RemoteController storedRemoteController = remoteControllerRepository.findById(id).orElse(null);
        storedRemoteController.setId(remoteControllerDto.getId());
        storedRemoteController.setCompatibleWith(remoteControllerDto.getCompatibleWith());
        storedRemoteController.setBatteryType(remoteControllerDto.getBatteryType());
        storedRemoteController.setName(remoteControllerDto.getName());
        storedRemoteController.setPrice(remoteControllerDto.getPrice());
        storedRemoteController.setBrand(remoteControllerDto.getBrand());
        storedRemoteController.setOriginalStock(remoteControllerDto.getOriginalStock());
        remoteControllerRepository.save(storedRemoteController);
    }

    public RemoteControllerDto transferToDto(RemoteController remoteController){
        var dto = new RemoteControllerDto();

        dto.id = remoteController.getId();
        dto.compatibleWith = remoteController.getCompatibleWith();
        dto.batteryType = remoteController.getBatteryType();
        dto.name = remoteController.getName();
        dto.brand = remoteController.getBrand();
        dto.price = remoteController.getPrice();
        dto.originalStock = remoteController.getOriginalStock();

        return dto;
    }

    public RemoteController transferToRemoteController(RemoteControllerDto dto){
        var remoteController = new RemoteController();

        remoteController.setId(dto.getId());
        remoteController.setCompatibleWith(dto.getCompatibleWith());
        remoteController.setBatteryType(dto.getBatteryType());
        remoteController.setName(dto.getName());
        remoteController.setBrand(dto.getBrand());
        remoteController.setPrice(dto.getPrice());
        remoteController.setOriginalStock(dto.getOriginalStock());

        return remoteController;
    }

}
