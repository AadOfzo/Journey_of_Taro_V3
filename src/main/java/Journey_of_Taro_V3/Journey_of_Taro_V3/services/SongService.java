package Journey_of_Taro_V3.Journey_of_Taro_V3.services;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.SongInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.RecordNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.SongTitleNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.Song;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SongService {

    @Autowired
    private final SongRepository songRepository;

    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    // Find all songs
    public List<SongDto> getAllSongs() {
        List<Song> songList = songRepository.findAll();
        return transferSongListToDtoList(songList);
    }

    // Find all songs by songtitle
    public List<SongDto> getAllSongsBySongTitle(String songtitle) {
        List<Song> songList = songRepository.findAllSongsBySongtitleEqualsIgnoreCase(songtitle);
        return transferSongListToDtoList(songList);
    }

    // Hier komen verschillende SongCollectionTypes in "if" statement Song to DTO list
    public List<SongDto> transferSongListToDtoList(List<Song> songs) {
        List<SongDto> songDtoList = new ArrayList<>();

        for (Song song : songs) {
            SongDto dto = tranferToSongDto(song);
            songDtoList.add(dto);
        }
        return songDtoList;
    }

    // Get Song By ID
    public SongDto getSongById(Long id) {

        if (songRepository.findById(id).isPresent()) {
            Song song = songRepository.findById(id).get();
            SongDto dto = tranferToSongDto(song);

            return tranferToSongDto(song);
        } else {
            throw new SongTitleNotFoundException("No song found with the title: ");
        }
    }

    public SongDto addSong(SongInputDto dto) {

        Song song = tranferToSong(dto);
        songRepository.save(song);

        return tranferToSongDto(song);
    }

    public void deleteSong(Long id) {
        songRepository.deleteById(id);
    }
    // Get song by title
    public static SongDto fromSong(Song song){

        var dto = new SongDto();

        dto.songtitle = song.getSongtitle();
        dto.isfavorited = song.getIsfavorited();

        return dto;
    }

    public Song toSong(SongDto songDto) {
        var song = new Song();

        song.setSongtitle(songDto.getSongtitle());
        song.setIsfavorited(songDto.getIsfavorited());

        return song;
    }
    public SongDto getSong(String songtitle) {
        SongDto dto = new SongDto();
        Optional<Song> song = songRepository.findById(songtitle);
        if (song.isPresent()){
            dto = fromSong(song.get());
        } else {
            throw new SongTitleNotFoundException(songtitle);
        }
        return dto;
    }

    // Update song ID
    public SongDto updateSong(Long id, SongInputDto inputDto) {

        if (songRepository.findById(id).isPresent()){

            Song song = songRepository.findById(id).get();

            Song song1 = tranferToSong(inputDto);
            song1.setId(song.getId());

            songRepository.save(song1);

            return tranferToSongDto(song1);

        } else {

            throw new RecordNotFoundException("No songs found");
        }
    }

    // Update songtitle
    public SongDto updateSong(String songtitle, SongInputDto inputDto) {

        if (songRepository.findById(songtitle).isPresent()){

            Song song = songRepository.findById(songtitle).get();

            Song song1 = tranferToSong(inputDto);
            song1.setSongtitle(song.getSongtitle());

            songRepository.save(song1);

            return tranferToSongDto(song1);

        } else {

            throw new RecordNotFoundException("No songs found");
        }
    }

    public Song tranferToSong(SongInputDto dto){
        var song = new Song();

        song.setSongtitle(song.getSongtitle());
        song.setIsfavorited(song.getIsfavorited());

        return song;
    }

    public SongDto tranferToSongDto(Song song){
        SongDto dto = new SongDto();

        dto.setId(song.getId());
        dto.setSongtitle(song.getSongtitle());
        dto.setIsfavorited(song.getIsfavorited());

        return dto;
    }

}
