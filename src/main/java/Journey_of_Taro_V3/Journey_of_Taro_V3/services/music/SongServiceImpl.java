package Journey_of_Taro_V3.Journey_of_Taro_V3.services.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.RecordNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.Song;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.SongCollectionType;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music.SongCollectionRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.music.SongRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SongServiceImpl {

    private SongRepository songRepository;
    private final SongCollectionRepository songCollectionRepository;
    private final SongCollectionService songCollectionService;
//    private final SongCollectionType songCollectionType;

    public SongServiceImpl(SongRepository songRepository, SongCollectionRepository songCollectionRepository, SongCollectionService songCollectionService
//                           SongCollectionType songCollectionType
    ) {
        this.songRepository = songRepository;
        this.songCollectionRepository = songCollectionRepository;
        this.songCollectionService = songCollectionService;
//        this.songCollectionType = songCollectionType;
    }

    public List<SongDto> getAllSongs() {
        List<Song> songList = songRepository.findAll();
        return transferSongListToDtoList(songList);
    }


    public List<SongDto> transferSongListToDtoList(List<Song> songs) {
        List<SongDto> songDtoList = new ArrayList<>();

        SongCollectionType songCollectionType = SongCollectionType.EPs;

        if (songCollectionType == SongCollectionType.EPs) {
            System.out.println("These are EP's");
        }

        for (SongCollectionType mySongCollectionType : SongCollectionType.values()) {
            System.out.println(mySongCollectionType);
        }


//        for(Song song : songs) {
//            SongDto dto = transferToDto(song);
//              if()
//            if(song.getSongCollection() != null){
//                dto.setSongCollectionDto(songCollectionService.tranferToDto(song.getSongCollection()));
//            }
//
//        }

        return songDtoList;
    }

    public static SongDto transferToSongDto(Song song) {
        var dto = new SongDto();

        dto.id = song.getId();
        dto.songTitle = song.getSongTitle();
        dto.artistName = song.getArtistName();

        return dto;
    }

//    public SongDto getSongById(Long id) {
//
//        Song song = null;
//        if (songRepository.findById(id).isPresent()) {
//            song = songRepository.findById(id).get();
//            SongDto songDto = transferToSongDto(song);
//        }
//        return transferToDto(song);
//    } else {
//        throw new RecordNotFoundException("Geen songs gevonden")
//        }

    public SongDto addSong(SongInputDto inputDto) {

        Song song = transferToSong(inputDto);
        songRepository.save(song);

        return transferToSongDto(song);
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

    public Song transferToSong(SongInputDto dto) {
        var song = new Song();

        song.setSongTitle(dto.getSongTitle());

        return song;
    }

//public SongDto tranferToSongDto(Song song){
//        SongDto dto=new SongDto();
//
//        dto.setId(song.getId());
//        dto.setSongTitle(song.setSongTitle());
//        dto.setArtistName(song.setArtistName());
//
//        if(song.getSongCollection()!=null){
//        dto.setSongCollectionDto(SongCollectionService.tranferToDto(song.getSongCollection()));
//        }
//
//        return dto;
//        }

//public void assignSongToSongCollection(Long id,Long songCollectionId){
//        var optionalSong=songRepository.findById(id);
//        var optionalSongCollection=songCollectionRepository.findById(songCollectionId);
//
//        if(optionalSong.isPresent()&&optionalSongCollection.isPresent()){
//        var song=optionalSong.get();
//        var songCollection=optionalSongCollection.get();
//
//        song.setSongCollection(songCollection);
//        songCollectionRepository.save(song);
//        }else{
//        throw new RuntimeException();
//        }
//        }

}
