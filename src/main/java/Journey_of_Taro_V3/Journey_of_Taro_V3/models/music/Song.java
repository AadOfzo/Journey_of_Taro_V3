package Journey_of_Taro_V3.Journey_of_Taro_V3.models.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.BadRequestException;
import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Entity
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] songData;

    private String songTitle;

    public String getName() {
        return null;
    }

    @Enumerated(EnumType.STRING)
    private SongCollectionType songCollectionType;
    private String artistName;

    @ManyToOne
    @JoinColumn(name = "song_collection_id")
    private SongCollection songCollection;

    public Song() {
    }

    public Song(String songTitle, MultipartFile songData, String artistName, String collectionType) {
        if (songTitle == null || songTitle.trim().isEmpty()) {
            throw new BadRequestException("Song title cannot be null or empty");
        }

        if (songData == null || songData.isEmpty()) {
            throw new BadRequestException("Please choose an mp3 Audio file");
        }

        if (collectionType == null || collectionType.trim().isEmpty()) {
            throw new BadRequestException("Please provide a collection type");
        }

        if (artistName == null || artistName.trim().isEmpty()) {
            throw new BadRequestException("Please provide an artist name");
        }

        this.songTitle = songTitle;
        this.artistName = artistName;
        this.songCollectionType = SongCollectionType.valueOf(collectionType);

        try {
            this.songData = songData.getBytes();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read song file data", e);
        }
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

    // Song to String
    @Override
    public String toString() {
        String string = songTitle + "" + songData + "" + artistName + "" + songCollectionType + "" ;
        if(songCollection != null) {
            string += " is in collection " + songCollection.toString() + ".";
        } else {
            string += " has no collection.";
        }
        return string;
//                "Request(songtitle=" + this.getOriginalAudioFilename() + ", songdata" + this.getSongData() + ", artistname" + this.getArtistName() + ", collection type" + this.getSongCollectionType();
    }

}

