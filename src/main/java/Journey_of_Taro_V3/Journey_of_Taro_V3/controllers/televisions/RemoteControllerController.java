package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.televisions;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.televisions.RemoteControllerDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.televisions.RemoteControllerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Dit is de Controller klasse van de RemoteController entiteit en heeft vergelijkbare methodes als de TelevisionController.
@RestController
public class RemoteControllerController {
    private final RemoteControllerService remoteControllerService;

    public RemoteControllerController(RemoteControllerService remoteControllerService) {
        this.remoteControllerService = remoteControllerService;
    }


    @GetMapping("/remotecontrollers")
    public ResponseEntity<List<RemoteControllerDto>> getAllRemotecontrollers() {

        List<RemoteControllerDto> dtos = remoteControllerService.getAllRemoteControllers();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/remotecontrollers/{id}")
    public ResponseEntity<RemoteControllerDto> getRemotecontroller(@PathVariable("id") Long id) {

        RemoteControllerDto dto = remoteControllerService.getRemoteController(id);

        return ResponseEntity.ok(dto);
    }

    @PostMapping("/remotecontrollers")
    public ResponseEntity<RemoteControllerDto> addRemoteController(@RequestBody RemoteControllerDto dto) {
        RemoteControllerDto dto1 = remoteControllerService.addRemoteController(dto);
        return ResponseEntity.created(null).body(dto1);
    }

    @DeleteMapping("/remotecontrollers/{id}")
    public ResponseEntity<Object> deleteRemoteController(@PathVariable("id") Long id) {
        Boolean check = remoteControllerService.deleteRemoteController(id);
        // Als de service methode een true returned (succesvolle delete), returnen we een noContent, anders returnen we een badRequest
        if(check) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().body("Het id dat je probeert te verwijderen bestaat niet.");
        }
    }

    @PutMapping("/remotecontrollers/{id}")
    public ResponseEntity<RemoteControllerDto> updateTelevision(@PathVariable("id") Long id, @RequestBody RemoteControllerDto dto) {
       remoteControllerService.updateRemoteController(id, dto);
        return ResponseEntity.ok(dto);
    }

}