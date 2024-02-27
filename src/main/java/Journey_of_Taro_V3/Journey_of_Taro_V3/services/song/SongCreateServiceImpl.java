package Journey_of_Taro_V3.Journey_of_Taro_V3.services.song;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.Song;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.IOException;
import java.time.LocalDateTime;
@Service
@Transactional
public class SongCreateServiceImpl implements SongCreateService {

    private final SongRepository songRepository;
    @Autowired
    public SongCreateServiceImpl(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public SongDto create(SongInputDto inputDto) {
        Song song = transferToSong(inputDto);
        song = songRepository.save(song);
        return transferToSongDto(song);
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
            e.printStackTrace();
        }

        return song;
    }

    SongDto transferToSongDto(Song song) {
        SongDto dto = new SongDto();
        dto.setId(song.getId());
        dto.setSongTitle(song.getSongTitle());
        return dto;
    }

    @Override
    public SongDto saveSong(SongInputDto inputDto) {
        Song song = transferToSong(inputDto);

        song = songRepository.save(song);

        return transferToSongDto(song);
    }
}
