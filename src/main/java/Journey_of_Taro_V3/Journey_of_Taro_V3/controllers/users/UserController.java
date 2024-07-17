package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.users;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.images.ImageInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.users.UserDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.BadRequestException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.RecordNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.CustomMultipartFile;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.images.Image;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.Song;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.users.User;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.images.ImageServiceImpl;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.music.SongServiceImpl;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.users.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@CrossOrigin
@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;
    private final ImageServiceImpl imageService;
    private final SongServiceImpl songService;
    private HttpServletRequest request;

    public UserController(UserService userService, ImageServiceImpl imageService, SongServiceImpl songService, HttpServletRequest request) {
        this.userService = userService;
        this.imageService = imageService;
        this.songService = songService;
        this.request = request;
    }

    // Get Mapping
    @GetMapping
    @Transactional
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping(value = "/username/{username}")
    public ResponseEntity<UserDto> getUser(@PathVariable("username") String username) {

        UserDto optionalUser = userService.getUserByUserName(username);

        return ResponseEntity.ok().body(optionalUser);

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long userId) {
        UserDto userDto = userService.getUserById(userId);
        if (userDto != null) {
            return ResponseEntity.ok().body(userDto);
        } else {
            return ResponseEntity.notFound().build();
        }
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

    @GetMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> getUserAuthorities(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(userService.getRoles(username));
    }

    @GetMapping("/{id}/images")
    public ResponseEntity<byte[]> getUserImage(@PathVariable("id") Long id, HttpServletRequest request) {
        Resource resource = userService.getImageFromUser(id);
        Image image = (Image) userService.getImageFromUser(id);

        String mimeType;

        try {
            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename" + image.getImageName())
                .body(image.getImageData());
    }

    @GetMapping("/{id}/songs")
    public ResponseEntity<byte[]> getUserSong(@PathVariable("id") String userName) {

        Song song = (Song) userService.getSongFromUser(userName);

        MediaType mediaType;

        try {
            mediaType = MediaType.parseMediaType(song.getSongUrl());
        } catch (InvalidMediaTypeException ignore) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        }

        return ResponseEntity
                .ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename" + song.getSongTitle())
                .body(song.getSongData());
    }

    // PUT mapping
    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("userId") Long userId, @RequestBody UserDto dto) {
        userService.updateUser(userId, dto);
        return ResponseEntity.noContent().build();
    }

    // Frontend Component UserList
    @PutMapping("/{username}/grant-admin")
    public ResponseEntity<Object> grantAdminPrivilege(@PathVariable("username") String username) {
        userService.grantAdminPrivilege(username);
        return ResponseEntity.ok().build();
    }

    // PUT update artistName for a user
    @PutMapping("/{id}/artistName")
    public ResponseEntity<Object> updateArtistName(@PathVariable("id") Long userId, @RequestParam("artistName") String artistName) {
        try {
            userService.updateArtistName(userId, artistName);
            return ResponseEntity.noContent().build();
        } catch (RecordNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // POST Mapping
    @PostMapping(value = "")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto dto) {
        String newUsername = userService.createUser(dto);
        userService.addRole(newUsername, "ROLE_USER");

        URI location = ServletUriComponentsBuilder.fromPath("/users/")
                .buildAndExpand(newUsername).toUri();

        return ResponseEntity.created(location).build();
    }

    @PostMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> addUserAuthority(@PathVariable("username") String username, @RequestBody Map<String, Object> fields) {
        try {
            String authorityName = (String) fields.get("authority");
            userService.addRole(username, authorityName);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            throw new BadRequestException();
        }
    }

    // POST add image to user
    @PostMapping("/{id}/image")
    public ResponseEntity<User> addImageToUser(@PathVariable("id") Long userId,
                                               @RequestParam("file") MultipartFile file) throws IOException {
        String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/users/")
                .path(userId.toString())
                .path("/image")
                .toUriString();

        ImageInputDto inputDto = new ImageInputDto();
        inputDto.setImageFile(new CustomMultipartFile(file.getOriginalFilename(), file.getContentType(), file.getBytes()));
        inputDto.setImageName(file.getOriginalFilename());
        inputDto.setImageAltName(file.getOriginalFilename());

        Image image = imageService.storeFile(file, imageUrl);
        User user = userService.assignImageToUser(userId, image);

        return ResponseEntity.created(URI.create(imageUrl)).body(user);
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable("username") String username) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

}