package Journey_of_Taro_V3.Journey_of_Taro_V3.services.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongCollectionDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongCollectionInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.RecordNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.Song;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.users.User;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music.SongRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.users.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SongServiceImpl implements SongService {

    private static final Logger logger = LoggerFactory.getLogger(SongServiceImpl.class);

    private final UserRepository userRepository;
    private final SongRepository songRepository;


    @Autowired
    public SongServiceImpl(SongRepository songRepository, UserRepository userRepository) {
        this.songRepository = songRepository;
        this.userRepository = userRepository;
    }

    @Override
    public SongDto getSongById(Long id) {

        if (songRepository.findById(id).isPresent()) {
            Song song = songRepository.findById(id).get();
            SongDto dto = transferToSongDto(song);

            return transferToSongDto(song);
        } else {
            throw new RecordNotFoundException("No Song found with ID:" + (id));
        }
    }

    @Override
    public List<SongDto> getAllSongs() {
        List<Song> songs = songRepository.findAll();
        return transferSongListToDtoList(songs);
    }

    @Override
    public SongDto addSong(SongInputDto inputDto) {
        // Fetch the User based on the artistName provided in the inputDto
        String artistName = inputDto.getArtistName();
        logger.info("Searching for user with artistName: {}", artistName);

        Optional<User> optionalUser = userRepository.findByArtistName(artistName);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            logger.info("User found with artistName: {}", artistName);

            // Transfer inputDto to Song entity
            Song song = transferToSong(inputDto);

            // Set the fetched User as the owner of the Song
            song.setArtistName(user);

            // Save the Song entity and assign the returned value
            song = songRepository.save(song);

            // Transfer the saved Song entity to SongDto and return
            return transferToSongDto(song);
        } else {
            logger.error("User not found with artistName: {}", artistName);
            throw new RecordNotFoundException("User not found with artistName: " + artistName);
        }
    }

    SongDto transferToSongDto(Song song) {
        SongDto dto = new SongDto();

        dto.setId(song.getId());
        dto.setSongTitle(song.getSongTitle());

        return dto;
    }

    private List<SongDto> transferSongListToDtoList(List<Song> songs) {
        List<SongDto> songDtoList = new ArrayList<>();
        for (Song song : songs) {
            songDtoList.add(transferToSongDto(song));
        }
        return songDtoList;
    }

    private Song transferToSong(SongInputDto dto) {
        Song song = new Song();
        // Song properties van Song en SongInputDto
        song.setFileName(dto.getSongFile().getOriginalFilename());
        song.setSongTitle(dto.getSongTitle());
        song.setFileSize(dto.getSongFile().getSize());
        song.setUploadTime(LocalDateTime.now());
        try {
            song.setSongData(dto.getSongFile().getBytes());
        } catch (IOException e) {
            // Handle IOException
            e.printStackTrace();
        }
        return song;
    }

    @Override
    public SongDto saveSong(SongInputDto inputDto) {
        // Fetch the User based on the artistName provided in the inputDto
        User user = userRepository.findByArtistName(inputDto.getArtistName())
                .orElseThrow(() -> new RecordNotFoundException("User not found with artistName: " + inputDto.getArtistName()));

        Song song = transferToSong(inputDto);

        song.setArtistName(user);

        song = songRepository.save(song);

        return transferToSongDto(song);
    }

    @Override
    public SongDto updateSong(Long id, SongInputDto inputDto) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No song found with ID: " + id));

        // Update song attributes based on the input DTO
        song.setSongTitle(inputDto.getSongTitle());

        Song updatedSong = songRepository.save(song);

        return transferToSongDto(updatedSong);
    }

    @Override
    public void deleteSong(Long id) {
        songRepository.deleteById(id);
    }

//    @Override
//    public SongCollectionDto addSongs(SongCollectionInputDto inputDto, SongDto dto) {
//        return null;
//    }
//
//    private Song convertToEntity(SongInputDto inputDto) throws IOException {
//        // Convert the input DTO to a Song entity
//        Song song = new Song();
//        song.setSongTitle(inputDto.getSongTitle());
//
//        return song;
//    }

}
