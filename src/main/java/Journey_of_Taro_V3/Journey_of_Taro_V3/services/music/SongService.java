package Journey_of_Taro_V3.Journey_of_Taro_V3.services.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.BadRequestException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.RecordNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.Song;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongService {

    private final SongRepository songRepository;

    @Autowired
    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public SongService() {
        this.songRepository = null;
    }

    public List<SongDto> getAllSongs() {
        List<Song> songs = songRepository.findAll();
        return songs.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public SongDto getSongById(Long id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No image found with the ID: " + id));
        return convertToDto(song);
    }

    public SongDto addSong(SongInputDto inputDto) {
        try {
            Song song = convertToEntity(inputDto);
            songRepository.save(song);
            return convertToDto(song);
        } catch (IOException e) {
            e.printStackTrace();

            throw new BadRequestException("Failed to add Song. Check your request data.");
        }
    }

    public void deleteSong(Long id) {
        songRepository.deleteById(id);
    }

    private SongDto convertToDto(Song song) {
        return new SongDto(song.getId(), song.getSongTitle());
    }

    private Song convertToEntity(SongInputDto inputDto) throws IOException {
        Song song = new Song();
        song.setSongTitle(inputDto.getSongTitle());

        byte[] songData = inputDto.getFile().getBytes();
        song.setSongData(songData);

        return song;
    }
}
