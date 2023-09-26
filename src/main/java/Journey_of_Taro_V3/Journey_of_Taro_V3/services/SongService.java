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

    // Find all songs by songTitle
    public List<SongDto> getAllSongsBySongTitle(String songTitle) {
        List<Song> songList = songRepository.findAllSongsBySongTitleEqualsIgnoreCase(songTitle);
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

        dto.songTitle = song.getSongTitle();
        dto.artistName = song.getArtistName();
        dto.isFavorited = song.getIsFavorited();

        return dto;
    }

    public Song toSong(SongDto songDto) {
        var song = new Song();

        song.setSongTitle(songDto.getSongTitle());
        song.setArtistName(songDto.getArtistName());
        song.setIsFavorited(songDto.getIsFavorited());

        return song;
    }
    public SongDto getSong(String songTitle) {
        Optional<Song> songOptional = songRepository.findById(songTitle);
        if (songOptional.isPresent()) {
            return tranferToSongDto(songOptional.get());
        } else {
            throw new RecordNotFoundException("No song found with the title: " + songTitle);
        }
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

    // Update songTitle
    public SongDto updateSong(String songTitle, SongInputDto inputDto) {

        if (songRepository.findById(songTitle).isPresent()){

            Song song = songRepository.findById(songTitle).get();

            Song song1 = tranferToSong(inputDto);
            song1.setSongTitle(song.getSongTitle());

            songRepository.save(song1);

            return tranferToSongDto(song1);

        } else {

            throw new RecordNotFoundException("No songs found");
        }
    }

    public Song tranferToSong(SongInputDto dto){
        var song = new Song();

        song.setSongTitle(dto.getSongTitle());
        song.setIsFavorited(dto.getIsFavorited());

        return song;
    }

    public SongDto tranferToSongDto(Song song){
        SongDto dto = new SongDto();

        dto.setId(song.getId());
        dto.setSongTitle(song.getSongTitle());
        dto.setIsFavorited(song.getIsFavorited());

        return dto;
    }

}
