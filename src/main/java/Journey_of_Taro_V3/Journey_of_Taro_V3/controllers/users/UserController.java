package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.users;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.users.UserDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.BadRequestException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.users.User;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.users.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@CrossOrigin
@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    @Autowired
    private final HttpServletRequest request;

    public UserController(UserService userService, HttpServletRequest request) {
        this.userService = userService;
        this.request = request;
    }

//    @GetMapping(value = "")
//    public ResponseEntity<List<UserDto>> getUsers() {
//
//        List<UserDto> userDtos = userService.getUsers();
//
//        return ResponseEntity.ok().body(userDtos);
//    }

    @GetMapping
    @Transactional
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

//    @PostMapping("/{id}/images")
//    public ResponseEntity<ImageDto> addImageToUser(@PathVariable("id") String apikey,
//                                                             @RequestBody MultipartFile imageFile)
//            throws IOException {
//        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/users/")
//                .path(Objects.requireNonNull(apikey))
//                .path("*/image")
//                .toUriString();
//        String imageName = imageFile.getOriginalFilename();
//        SongCollection songCollection = userService.addImageToUser(imageName, apikey);
//
//        return ResponseEntity.created(URI.create(url)).body(user);
//
//    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<UserDto> getUser(@PathVariable("username") String username) {

        UserDto optionalUser = userService.getUser(username);


        return ResponseEntity.ok().body(optionalUser);

    }

    @GetMapping(value = "/apikey/{apikey}")
    public ResponseEntity<UserDto> getUserByApiKey(@PathVariable("apikey") String apikey) {
        UserDto userDto = userService.getUserByApiKey(apikey);
        if (userDto != null) {
            return ResponseEntity.ok().body(userDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping(value = "/users/create")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto dto) {

        String newUsername = userService.createUser(dto);
        userService.addRole(newUsername, "ROLE_USER");

        URI location = ServletUriComponentsBuilder.fromPath("/users/{username}")
                .buildAndExpand(newUsername).toUri();

        return ResponseEntity.created(location).build();
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody UserDto userDto) {
        User savedUser = userService.saveUser(userDto);
        String url = ServletUriComponentsBuilder.fromContextPath(request)
                .path("/users/save")
                .path(Objects.requireNonNull(savedUser.getApikey().toString()))
                .toUriString();
        return ResponseEntity.created(URI.create(url)).body(savedUser);
    }

    @GetMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> getUserAuthorities(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(userService.getRoles(username));
    }

    @PutMapping(value = "/{username}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("username") String username, @RequestBody UserDto dto) {

        userService.updateUser(username, dto);

        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> addUserAuthority(@PathVariable("username") String username, @RequestBody Map<String, Object> fields) {
        try {
            String authorityName = (String) fields.get("authority");
            userService.addRole(username, authorityName);
            return ResponseEntity.noContent().build();
        }
        catch (Exception ex) {
            throw new BadRequestException();
        }
    }


    @DeleteMapping(value = "/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable("username") String username) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

}