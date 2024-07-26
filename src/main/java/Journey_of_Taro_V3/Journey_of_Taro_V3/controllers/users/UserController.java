package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers.users;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.users.UserDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.BadRequestException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.RecordNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.Song;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.users.User;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.images.ImageServiceImpl;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.music.SongServiceImpl;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.users.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
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

    // POST Mapping
    @PostMapping(value = "")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto dto) {
        String newUsername = userService.createUser(dto);
        userService.addRole(newUsername, "USER");

        URI location = ServletUriComponentsBuilder.fromPath("/users/")
                .buildAndExpand(newUsername).toUri();

        return ResponseEntity.created(location).build();
    }

    // PUT mapping
    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long userId, @RequestBody UserDto dto) {
        userService.updateUser(userId, dto);
        return ResponseEntity.noContent().build();
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

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable("username") String username) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

    // User --> Authorities endpoints
    @GetMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> getUserAuthorities(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(userService.getRoles(username));
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
    @PutMapping("/{username}/grant-admin")
    public ResponseEntity<Object> grantAdminPrivilege(@PathVariable("username") String username) {
        userService.grantAdminPrivilege(username);
        return ResponseEntity.ok().build();
    }

    // Users & images endpoinds
    @GetMapping("/{id}/image")
    public ResponseEntity<Resource> getUserImage(@PathVariable("id") Long userId, HttpServletRequest request) {
        Resource resource = userService.getImageFromUser(userId);

        String mimeType;
        try {
            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping("/{id}/image")
    public ResponseEntity<?> addImageToUser(@PathVariable("id") Long userId,
                                            @RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty");
            }

            String imageName = imageService.storeFile(file);
            User user = userService.assignImageToUser(userId, imageName);

            // Log the URI being created
            URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/users/{id}/image")
                    .buildAndExpand(userId)
                    .toUri();

            System.out.println("Created URI location: " + location.toString());

            return ResponseEntity.created(location).body(user);
        } catch (IOException e) {
            e.printStackTrace(); // Log the stack trace for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not store file");
        } catch (Exception e) {
            e.printStackTrace(); // Log the stack trace for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }


//    @PostMapping("/{id}/image")
//    public ResponseEntity<User> addImageToUser(@PathVariable("id") Long userId,
//                                               @RequestParam("file") MultipartFile file) {
//        try {
//            if (file.isEmpty()) {
//                return ResponseEntity.badRequest().body(null);
//            }
//
//            String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
//                    .path("/users/")
//                    .path(userId.toString())
//                    .path("/uploads/images/")
//                    .path(file.getOriginalFilename())
////                    .path("/image")
//                    .toUriString();
//
//            String imageName = imageService.storeFile(file);
//            User user = userService.assignImageToUser(userId, imageName);
//
//            return ResponseEntity.created(URI.create(imageUrl)).body(user);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

    // Users & Songs endpoints
    @GetMapping("/{id}/songs")
    public ResponseEntity<byte[]> getUserSong(@PathVariable("id") Long userId) {

        Song song = (Song) userService.getSongFromUser(userId);

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

    @PostMapping("/{id}/song")
    public ResponseEntity<User> addSongToUser(@PathVariable("id") Long userId,
                                               @RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("Received request to add song for user with ID: " + userId + " and file: " + file.getOriginalFilename());

        String songUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/users/")
                .path(Objects.requireNonNull(userId.toString()))
                .path("/song")
                .toUriString();

        // Retrieve the user by ID
        UserDto userDto = userService.getUserById(userId);
        if (userDto == null) {
            return ResponseEntity.notFound().build();
        }

        // Prepare SongInputDto
        SongInputDto inputDto = new SongInputDto();
        inputDto.setSongFile(file);
        inputDto.setSongTitle(file.getOriginalFilename());
        inputDto.setArtistName(userDto.getArtistname()); // Artist name as a string
        inputDto.setFileName(file.getOriginalFilename());
        inputDto.setFileSize(file.getSize());
        inputDto.setUploadTime(LocalDateTime.now());

        String songTitle = songService.storeSongFile(file);
        User user = userService.assignSongToUser(userId, songTitle);

        return ResponseEntity.created(URI.create(songUrl)).body(user);
    }

}