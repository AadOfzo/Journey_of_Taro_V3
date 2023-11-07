package Journey_of_Taro_V3.Journey_of_Taro_V3.services.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.RecordNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.Song;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.users.User;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongService {

    private final SongRepository songRepository;

    @Autowired
    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public List<SongDto> getAllSongs() {
        List<Song> songs = songRepository.findAll();
        return transferSongListToDtoList(songs);
    }

    // Find all songs by ID
    public SongDto getSongById(Long id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No song found with the ID: " + id));
        return transferToSongDto(song);
    }

    public SongDto addSong(SongInputDto dto) {
        Song song = transferToSong(dto);
        songRepository.save(song);
        return transferToSongDto(song);
    }

    public void deleteSong(Long id) {
        songRepository.deleteById(id);
    }

    public SongDto updateSong(Long id, SongInputDto inputDto) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No songs found with the ID: " + id));

        Song updatedSong = transferToSong(inputDto);
        updatedSong.setId(id);

        songRepository.save(updatedSong);

        return transferToSongDto(updatedSong);
    }

    public Song transferToSong(SongInputDto dto) {
        Song song = new Song();

        song.setSongTitle(dto.getSongTitle());
        return song;
    }

    public SongDto transferToSongDto(Song song) {
        SongDto dto = new SongDto();

        dto.setId(song.getId());
        dto.setSongTitle(song.getSongTitle());
        return dto;
    }

    public List<SongDto> transferSongListToDtoList(List<Song> songs) {
        return songs.stream().map(this::transferToSongDto).collect(Collectors.toList());
    }
}
