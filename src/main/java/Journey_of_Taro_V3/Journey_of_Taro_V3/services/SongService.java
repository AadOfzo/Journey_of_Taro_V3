package Journey_of_Taro_V3.Journey_of_Taro_V3.services;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.SongInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.RecordNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.SongTitleNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.Song;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.SongRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SongService {

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
        Optional<Song> song = songRepository.findById(id);
        if (song.isPresent()) {
            Song song1 = song.get();
            SongDto songDto = new SongDto();
            songDto.setId(song1.getId());
            songDto.setSongtitle(song1.getSongtitle());
            songDto.setArtistname(song1.getArtistname());
            songDto.setIsfavorited(song1.getIsfavorited());

            return songDto;
        } else {
            throw new SongTitleNotFoundException("No song found with the ID: " + id);
        }
    }

    public SongDto addSong(SongInputDto song) {
        Song song1 = new Song();
        song1.setId(song1.getId());
        song1.setSongtitle(song.getSongtitle());
        song1.setArtistname(song.getArtistname());
        song1.setIsfavorited(song.getIsfavorited());
        songRepository.save(song1);

        SongDto songDto = new SongDto();
        songDto.setId(song1.getId());
        songDto.setSongtitle(song1.getSongtitle());
        songDto.setArtistname(song1.getArtistname());
        songDto.setIsfavorited(song1.getIsfavorited());

        return songDto;
    }
    public void deleteSong(Long id) {
        songRepository.deleteById(id);
    }

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
    // Update song ID
//    public Song updateSong(Long id, Song inputSong) {
//        Optional<Song> song = songRepository.findById(id);
//        if (song.isPresent()){
//            Song existingSong = song.get();
//            inputSong.setId(existingSong.getId());
//            return songRepository.save(inputSong);
//        } else {
//            throw new RecordNotFoundException("No song found with the ID: " + id);
//        }
//    }
    // Update songtitle
//    public Song updateSong(String songtitle, Song inputSong) {
//        Optional<Song> song = songRepository.findBySongtitleIgnoreCase(songtitle);
//        if (song.isPresent()){
//            Song existingSong = song.get();
//            inputSong.setId(existingSong.getId());
//            return songRepository.save(inputSong);
//        } else {
//            throw new RecordNotFoundException("No song found with the title: " + songtitle);
//        }
//    }
    public Song tranferToSong(SongInputDto dto){
        var song = new Song();

        song.setSongtitle(dto.getSongtitle());
        song.setArtistname(dto.getArtistname());
        song.setIsfavorited(dto.getIsfavorited());

        return song;
    }

    public SongDto tranferToSongDto(Song song){
        SongDto dto = new SongDto();

        dto.setId(song.getId());
        dto.setSongtitle(song.getSongtitle());
        dto.setArtistname(song.getArtistname());
        dto.setIsfavorited(song.getIsfavorited());

        return dto;
    }
}
