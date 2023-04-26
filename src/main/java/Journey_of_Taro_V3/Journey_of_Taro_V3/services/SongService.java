package Journey_of_Taro_V3.Journey_of_Taro_V3.services;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.SongInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.RecordNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.SongTitleNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.Song;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.SongCollectionType;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.SongRepository;
import jakarta.annotation.PostConstruct;
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

    // Find all songs by Id
    public List<SongDto> getSongById(Long id) {
        List<Song> songList = songRepository.findAllSongsByIdEqualsIgnoreCase(id);
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

    // Assign Song to SongCollectionType
    private List<SongCollectionType> songCollectionTypes = new ArrayList<>();

    // Initialize named song collection types of different types
    @PostConstruct
    public void init() {
        songCollectionTypes.add(new SongCollectionType("demo"));
        songCollectionTypes.add(new SongCollectionType("sampledemo"));
        songCollectionTypes.add(new SongCollectionType("single"));
        songCollectionTypes.add(new SongCollectionType("ep"));
        songCollectionTypes.add(new SongCollectionType("album"));
        songCollectionTypes.add(new SongCollectionType("meditations"));
    }

    public void addSong(String type, Song song) throws IllegalArgumentException {
        SongCollectionType collectionType = getCollectionTypeByType(type);
        if (collectionType == null) {
            throw new IllegalArgumentException("Invalid collection type");
        }
        collectionType.addSong(song);
    }

    public List<Song> getAllSongs(String type) throws IllegalArgumentException {
        SongCollectionType collectionType = getCollectionTypeByType(type);
        if (collectionType == null) {
            throw new IllegalArgumentException("Invalid collection type");
        }
        return collectionType.getSongs();
    }

    private SongCollectionType getCollectionTypeByType(String type) {
        for (SongCollectionType collectionType : songCollectionTypes) {
            if (collectionType.getType().equals(type)) {
                return collectionType;
            }
        }
        return null;
    }

    // Add Song by ID
    public SongDto addSong(SongInputDto dto) {

        Song song = tranferToSong(dto);
        songRepository.save(song);

        return tranferToSongDto(song);
    }
    // Delete Song by ID
    public void deleteSong(Long id) {
        songRepository.deleteById(id);
    }
    // Get song by title
    public static SongDto fromSong(Song song){

        var dto = new SongDto();

        dto.songtitle = song.getSongtitle();
        dto.artistname = song.getArtistname();
        dto.isfavorited = song.getIsfavorited();
        return dto;
    }

    public Song toSong(SongDto songDto) {
        var song = new Song(songDto.getId(), songDto.getSongtitle(), songDto.getArtistname(), songDto.getIsfavorited(), songDto.songCollectionType);
        song.setId(song.getId());
        song.setSongtitle(songDto.getSongtitle());
        song.setArtistname(songDto.getArtistname());
        song.setIsfavorited(songDto.getIsfavorited());
        song.setSongCollectionType((SongCollectionType) songDto.songCollectionType);
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
            song1.setId(song.getId());

            songRepository.save(song1);

            return tranferToSongDto(song1);

        } else {

            throw new RecordNotFoundException("No songs found");
        }
    }

    public Song tranferToSong(SongInputDto dto){
        var song = new Song(dto.getSongtitle(), dto.getArtistname(), dto.getIsfavorited(), dto.);

        song.setSongtitle(dto.getSongtitle());
        song.setArtistname(dto.getArtistname());
        song.setIsfavorited(dto.getIsfavorited());
        song.setSongCollectionType(dto.getSongCollectionType());
        return song;
    }

    public static SongDto tranferToSongDto(Song song){
        var dto = new SongDto();
        dto.songtitle = song.getSongtitle();
        dto.artistname = song.getArtistname();
        dto.isfavorited = song.getIsfavorited();
        dto.songCollectionType = song.getSongCollectionType();
        return dto;
    }

}
