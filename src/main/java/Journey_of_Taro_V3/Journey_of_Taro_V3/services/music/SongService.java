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

    private SongRepository songRepository;

    @Autowired
    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
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

    public SongDto addSong(SongInputDto inputDto) {
        try {
            Song song = convertToEntity(inputDto);
            songRepository.save(song);
            return transferToSongDto(song);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BadRequestException("Failed to add Song. Check your request data.");
        }
    }

    public void deleteSong(Long id) {
        songRepository.deleteById(id);
    }

//    public SongDto updateSong(Long id, SongInputDto inputDto) {
//
//        if (songRepository.findById(id).isPresent()){
//
//            Song song = songRepository.findById(id).get();
//
//            Song song1 = transferToSong(inputDto);
//            song1.setId(song1);
//
//            return transferToSongDto(song1);
//
//        } else {
//
//            throw new RecordNotFoundException("No Songs found");
//        }
//    }

   public Song transferToSong(SongInputDto dto){
        var song = new Song();

        song.setSongTitle(dto.getSongTitle());

        return song;
   }

    private SongDto transferToSongDto(Song song) {
        return new SongDto(song.getId(), song.getSongTitle(), song.getArtistName());
    }

    private Song convertToEntity(SongInputDto inputDto) throws IOException {
        Song song = new Song();
        song.setSongTitle(inputDto.getSongTitle());

        byte[] songData = inputDto.getSongFile().getBytes();
        song.setSongData(songData);

        return song;
    }
}
