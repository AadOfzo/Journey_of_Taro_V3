package Journey_of_Taro_V3.Journey_of_Taro_V3.services.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.BadRequestException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.RecordNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.CustomMultipartFile;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.Song;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music.SongRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongService {

    private static final Logger logger = LoggerFactory.getLogger(SongService.class);

    private SongRepository songRepository;

    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    private Song convertToEntity(SongInputDto inputDto) throws IOException {
        Song song = new Song();
        song.setSongTitle(inputDto.getSongTitle());

        // Get the byte array from the SongInputDto
        byte[] songFileBytes = inputDto.getSongFile().getBytes();

        // Use the appropriate values for the CustomMultipartFile constructor
        CustomMultipartFile songFile = new CustomMultipartFile(
                inputDto.getSongTitle(), // Use song title or any appropriate name
                inputDto.getSongTitle(), // Use song title or any appropriate original filename
                songFileBytes
        );

        song.setSongFile(songFile);

        return song;
    }

    public List<SongDto> getSongs() {
        List<Song> songs = songRepository.findAll();
        return songs.stream().map(this::transferToSongDto).collect(Collectors.toList());
    }

    public List<SongDto> getAllSongs() {
        List<Song> songs = songRepository.findAll();
        return songs.stream().map(this::transferToSongDto).collect(Collectors.toList());
    }

    public SongDto getSongById(Long id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No song found with the ID: " + id));
        return transferToSongDto(song);
    }

    private Song convertFileToSongEntity(SongInputDto inputDto) throws IOException {
        Song song = new Song();
        song.setSongTitle(inputDto.getSongTitle());
        // Logic to handle file conversion to entity...
        byte[] songFileBytes = inputDto.getSongFile().getBytes();

        return song;
    }

    public SongDto addSong(SongInputDto inputDto) {
        try {
            logger.info("Starting to add a new song...");
            // Use the enhanced convertFileToSongEntity method to handle file conversion
            Song song = convertFileToSongEntity(inputDto);
            songRepository.save(song);
            logger.info("Song added successfully: {}", song);
            return transferToSongDto(song);
        } catch (IOException e) {
            logger.error("Failed to add Song. Check your request data.", e);
            throw new BadRequestException("Failed to add Song. Check your request data.");
        }
    }


    public SongDto updateSong(Long id, SongInputDto inputDto) {

        if (songRepository.findById(id).isPresent()) {

            Song song = songRepository.findById(id).get();

            Song song1 = transferToSong(inputDto);
            song1.setId(song.getId());

            songRepository.save(song1);

            return transferToSongDto(song1);

        } else {
            throw new RecordNotFoundException("No Songs found");
        }
    }

    public void deleteSong(Long id) {
        songRepository.deleteById(id);
    }


    public Song transferToSong(SongInputDto dto){
        var song = new Song();

        song.setSongTitle(dto.getSongTitle());

        return song;
    }

    private SongDto transferToSongDto(Song song) {
        return new SongDto(song.getId(), song.getSongTitle(), song.getArtistName());
    }

}