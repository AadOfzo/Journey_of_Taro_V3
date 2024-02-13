package Journey_of_Taro_V3.Journey_of_Taro_V3.services.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.BadRequestException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.RecordNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.Song;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music.SongRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongServiceImpl implements SongService {

    private static final Logger logger = LoggerFactory.getLogger(SongServiceImpl.class);

    private final SongRepository songRepository;

    public SongServiceImpl(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public List<SongDto> getAllSongs() {
        List<Song> songs = songRepository.findAll();
        return songs.stream()
                .map(this::transferToSongDto)
                .collect(Collectors.toList());
    }

    @Override
    public SongDto getSongById(Long id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No song found with ID: " + id));
        return transferToSongDto(song);
    }

    @Override
    public SongDto addSong(SongInputDto inputDto) {
        try {
            Song song = convertToEntity(inputDto);
            songRepository.save(song);
            return transferToSongDto(song);
        } catch (IOException e) {
            logger.error("Failed to add Song. Check your request data.", e);
            throw new BadRequestException("Failed to add Song. Check your request data.");
        }
    }

    @Override
    public void deleteSong(Long id) {
        if (!songRepository.existsById(id)) {
            throw new RecordNotFoundException("No song found with ID: " + id);
        }
        songRepository.deleteById(id);
    }

    @Override
    public SongDto updateSong(Long id, SongInputDto inputDto) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No song found with ID: " + id));

        // Update song attributes based on the input DTO
        song.setSongTitle(inputDto.getSongTitle());
        // Update other attributes as needed...

        // Save the updated song
        Song updatedSong = songRepository.save(song);

        return transferToSongDto(updatedSong);
    }

    private Song convertToEntity(SongInputDto inputDto) throws IOException {
        // Convert the input DTO to a Song entity
        Song song = new Song();
        song.setSongTitle(inputDto.getSongTitle());

        // Additional conversion logic as needed...

        return song;
    }

    private SongDto transferToSongDto(Song song) {
        // Convert a Song entity to a SongDto
        return new SongDto(song.getId(), song.getSongTitle(), song.getArtistName());
    }


//    private final SongRepository songRepository;
//    private final SongCollectionRepository songCollectionRepository;
//    private final SongCollectionService songCollectionService;
//
//    public SongServiceImpl(SongRepository songRepository, SongCollectionRepository songCollectionRepository, SongCollectionService songCollectionService) {
//        this.songRepository = songRepository;
//        this.songCollectionRepository = songCollectionRepository;
//        this.songCollectionService = songCollectionService;
//    }
//
//    public List<SongDto> getAllSongs() {
//        List<Song> songs = songRepository.findAll();
//        return transferSongListToDtoList(songs);
//    }
//
//    private List<SongDto> transferSongListToDtoList(List<Song> songs) {
//        List<SongDto> songDtoList = new ArrayList<>();
//        for (Song song : songs) {
//            SongDto dto = transferToSongDto(song);
//            songDtoList.add(dto);
//        }
//        return songDtoList;
//    }
//
//    private SongDto transferToSongDto(Song song) {
//        SongDto dto = new SongDto();
//        dto.setId(song.getId());
//        dto.setSongTitle(song.getSongTitle());
//        dto.setArtistName(song.getArtistName());
//        return dto;
//    }
//
//    public SongDto addSong(SongInputDto inputDto) {
//        Song song = transferToSong(inputDto);
//        songRepository.save(song);
//        return transferToSongDto(song);
//    }
//
//    public void deleteSong(Long id) {
//        songRepository.deleteById(id);
//    }
//
//    private Song transferToSong(SongInputDto dto) {
//        Song song = new Song();
//        song.setSongTitle(dto.getSongTitle());
//        return song;
//    }
}
