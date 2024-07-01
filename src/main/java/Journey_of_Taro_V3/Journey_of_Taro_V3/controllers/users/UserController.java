package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.users;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.users.UserDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.users.User;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.images.ImageServiceImpl;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.music.SongServiceImpl;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.users.UserService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ImageServiceImpl imageService;
    private final SongServiceImpl songService;

    public UserController(UserService userService, ImageServiceImpl imageService, SongServiceImpl songService) {
        this.userService = userService;
        this.imageService = imageService;
        this.songService = songService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @PostMapping
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        String username = userService.createUser(userDto);
        return ResponseEntity.created(URI.create("/users/" + username)).body(userDto);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> getUser(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUser(username));
    }

    @GetMapping("/{apikey}/apikey")
    public ResponseEntity<UserDto> getUserByApiKey(@PathVariable String apikey) {
        return ResponseEntity.ok(userService.getUserByApiKey(apikey));
    }

    @GetMapping("/artist/{artistName}")
    public ResponseEntity<UserDto> getUserByArtistName(@PathVariable String artistName) {
        return ResponseEntity.ok(userService.getArtistName(artistName));
    }

    @PutMapping("/{username}")
    public ResponseEntity<Void> updateUser(@PathVariable String username, @RequestBody UserDto userDto) {
        userService.updateUser(username, userDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{username}/roles")
    public ResponseEntity<List<String>> getUserRoles(@PathVariable String username) {
        return ResponseEntity.ok(userService.getRoles(username));
    }

    @PostMapping("/{username}/roles")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Void> addUserRole(@PathVariable String username, @RequestBody String roleName) {
        userService.addRole(username, roleName);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{username}/grant-admin")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Void> grantAdminPrivilege(@PathVariable String username) {
        userService.grantAdminPrivilege(username);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}/artistname")
    public ResponseEntity<Void> updateArtistName(@PathVariable Long userId, @RequestBody String artistName) {
        userService.updateArtistName(userId, artistName);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{apikey}/image")
    public ResponseEntity<UserDto> addImageToUser(@RequestParam("file") MultipartFile imageFile, @PathVariable String apikey) {
        User user = userService.addImageToUser(imageFile, apikey);
        return ResponseEntity.ok(UserService.fromUser(user));
    }

    @PostMapping("/{apikey}/song")
    public ResponseEntity<UserDto> addSongToUser(@RequestParam("file") MultipartFile songFile, @PathVariable String apikey) {
        User user = userService.addSongToUser(songFile, apikey);
        return ResponseEntity.ok(UserService.fromUser(user));
    }

    @GetMapping("/{userId}/image")
    public ResponseEntity<Resource> getImageFromUser(@PathVariable Long userId) {
        Resource resource = userService.getImageFromUser(userId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/{apikey}/song")
    public ResponseEntity<Resource> getSongFromUser(@PathVariable String apikey) {
        Resource resource = userService.getSongFromUser(apikey);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
