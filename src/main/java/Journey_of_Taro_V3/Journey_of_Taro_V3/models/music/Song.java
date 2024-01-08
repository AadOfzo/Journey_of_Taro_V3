package Journey_of_Taro_V3.Journey_of_Taro_V3.models.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.BadRequestException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

@Entity
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue
    private Long id;

    @Lob
    private byte[] songData;

    private String songTitle;
    @Enumerated(EnumType.STRING)
    private SongCollectionType songCollectionType;

    private String artistName;

    @ManyToOne
    @JoinColumn(name = "song_collection_id")
    private SongCollection songCollection;

    public Song() {
    }

    public Song(String songTitle, MultipartFile file, String artistName, SongCollectionType songCollectionType) {
        if (songTitle == null || songTitle.trim().isEmpty()) {
            throw new BadRequestException("Song title cannot be null or empty");
        }

        if (file == null || file.isEmpty()) {
            throw new BadRequestException("Please choose a mp3 file");
        }

        if (songCollectionType == null) {
            throw new BadRequestException("Please choose a collection type ");
        }

        if (artistName == null) {
            throw new BadRequestException("Please provide an artist name");
        }

        this.songTitle = songTitle;
        try {
            this.songData = file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file data", e);
        }
        this.songCollectionType = songCollectionType;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getSongData() {
        return songData;
    }

    public void setSongData(byte[] songData) {
        this.songData = songData;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public SongCollectionType getSongCollectionType() {
        return songCollectionType;
    }

    public void setSongCollectionType(SongCollectionType songCollectionType) {
        this.songCollectionType = songCollectionType;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public SongCollection getSongCollection() {
        return songCollection;
    }

    public void setSongCollection(SongCollection songCollection) {
        this.songCollection = songCollection;
    }
}
