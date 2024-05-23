package Journey_of_Taro_V3.Journey_of_Taro_V3.services.files.music;


import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongInputDto;
//import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.ArtistNameNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.RecordNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.CustomMultipartFile;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.Song;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.UserSong;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.users.User;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music.SongRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.users.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class SongServiceImpl implements SongService {
    private final Path fileStoragePath;
    private final String fileStorageLocation;
    private static final Logger logger = LoggerFactory.getLogger(SongServiceImpl.class);

    private final UserRepository userRepository;
    private final SongRepository songRepository;


    @Autowired
    public SongServiceImpl(@Value("uploads/songs") String fileStorageLocation, SongRepository songRepository, UserRepository userRepository) throws IOException {
        fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();
        this.fileStorageLocation = fileStorageLocation;
        this.songRepository = songRepository;
        this.userRepository = userRepository;

        Files.createDirectories(fileStoragePath);
    }

    @Override
    public SongDto getSong(Long id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Song not found"));

        return transferToSongDto(song);
    }

    @Override
    public SongDto getSongById(Long id) {
        if (songRepository.findById(id).isPresent()) {
            Song song = songRepository.findById(id).get();
            return transferToSongDto(song);
        } else {
            throw new RecordNotFoundException("No Songs found with id: " + id);
        }
    }

    @Override
    public List<SongDto> getAllSongs() {
        List<Song> songs = songRepository.findAll();
        return transferSongListToDtoList(songs);
    }


    @Override
    public SongDto addSong(SongInputDto inputDto) throws IOException {
        // Fetch the User based on the artistName provided in the inputDto
        String artistName = inputDto.getArtistName();
        logger.info("Searching for user with artistName: {}", artistName);

        Optional<User> optionalUser = userRepository.findByArtistName(artistName);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            logger.info("User found with artistName: {}", artistName);

            // Transfer inputDto to Song entity
            Song song = transferToSong(inputDto);

            // Set the fetched User as the owner of the Song
            song.setArtistName(user);

            // Set the song URL based on the fileStorageLocation and song title
            String songUrl = fileStorageLocation + "/" + song.getFileName();
            song.setSongUrl(songUrl);

            // Save the Song entity and assign the returned value
            song = songRepository.save(song);

            // Transfer the saved Song entity to SongDto and return
            return transferToSongDto(song);
        } else {
            logger.error("User not found with artistName: {}", artistName);
            throw new RecordNotFoundException("User not found with artistName: " + artistName);
        }
    }

    @Override
    public SongDto saveSong(SongInputDto inputDto) {

        User user = userRepository.findByArtistName(inputDto.getArtistName())
                .orElseThrow(() -> new RecordNotFoundException("User not found with artistName: " + inputDto.getArtistName()));

        Song song = transferToSong(inputDto);

        song.setArtistName(user);

        song = songRepository.save(song);

        return transferToSongDto(song);
    }

    public String storeSongFile(MultipartFile file) throws IOException {
        String songTitle = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Path filePath = Paths.get(String.valueOf(fileStoragePath), songTitle);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        songRepository.save(new UserSong(songTitle));
        return songTitle;
    }

    public List<SongDto> transferSongListToDtoList(List<Song> songs) {
        List<SongDto> songDtoList = new ArrayList<>();
        for (Song song : songs) {
            songDtoList.add(transferToSongDto(song));
        }
        return songDtoList;
    }

    private SongDto transferToSongDto(Song song) {
        SongDto dto = new SongDto();

        dto.setId(song.getId());
        dto.setSongTitle(song.getSongTitle());
        dto.setSongUrl(song.getSongUrl());
        dto.setSongData(song.getSongData());

        // Check if the artistName is not null before accessing its username
        if (song.getArtistName() != null) {
            dto.setArtistName(song.getArtistName().getUsername());
        }

        return dto;
    }

    private Song transferToSong(SongInputDto dto) {
        Song song = new Song();
        song.setFileName(dto.getSongFile().getOriginalFilename());
        song.setSongTitle(dto.getSongTitle());
        song.setFileSize(dto.getSongFile().getSize());
        song.setUploadTime(LocalDateTime.now());
        try {
            song.setSongData(dto.getSongFile().getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Error creating song entity", e);
        }
        return song;
    }

    public Resource downloadSongFile(String songTitle) {

        Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(songTitle);

        Resource resource;

        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException("Issue reading Songfile. ");
        }

        if (resource.exists()&& resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("The songfile doesn't exist or isn't readable");
        }
    }

    @Override
    public SongDto updateSong(Long id, SongInputDto inputDto) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No song found with ID: " + id));

        // Update song attributes based on the input DTO
        song.setSongTitle(inputDto.getSongTitle());

        // Set the song URL based on the fileStorageLocation and song title
        String songUrl = fileStorageLocation + "/" + song.getFileName();
        song.setSongUrl(songUrl);

        Song updatedSong = songRepository.save(song);

        return transferToSongDto(updatedSong);
    }

    @Override
    public void deleteSong(Long id) {
        songRepository.deleteById(id);
    }

    @Override
    public Song getSongWithData(String songTitle) {
        Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(songTitle);

        try {
            byte[] songData = Files.readAllBytes(path);
            Song song = new Song();
            song.setSongData(songData);
            return song;
        } catch (IOException e) {
            throw new RuntimeException("Issue reading Song file: ", e);
        }
    }

    @Override
    public String getSongUrlByTitle(String songTitle) {
        Optional<Song> optionalSong = songRepository.findBySongTitle(songTitle);
        if (optionalSong.isPresent()) {
            Song song = optionalSong.get();
            return song.getSongUrl();
        } else {
            throw new RecordNotFoundException("No song found with title: " + songTitle);
        }
    }



}
