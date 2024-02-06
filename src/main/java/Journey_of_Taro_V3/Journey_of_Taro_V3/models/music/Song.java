package Journey_of_Taro_V3.Journey_of_Taro_V3.models.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.BadRequestException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.CustomMultipartFile;
import jakarta.persistence.*;

import java.io.IOException;

@Entity
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] songDataBytes; // store the actual byte[] data

    @Transient
    private CustomMultipartFile songFile; // transient, not persisted in the database

    private String songTitle;

    @Enumerated(EnumType.STRING)
    private SongCollectionType songCollectionType;
    private String artistName;

    @ManyToOne
    @JoinColumn(name = "song_collection_id")
    private SongCollection songCollection;

    public Song() {
    }

    public Song(String songTitle, CustomMultipartFile songFile, String artistName, String collectionType) {
        if (songTitle == null || songTitle.trim().isEmpty()) {
            throw new BadRequestException("Song title cannot be null or empty");
        }

        if (songFile == null || songFile.isEmpty()) {
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

        // Convert CustomMultipartFile to byte[]
        try {
            this.songDataBytes = songFile.getBytes();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read song file data", e);
        }

        // Set the transient field
        this.songFile = songFile;
    }


    // Getter for CustomMultipartFile, which converts byte[] to CustomMultipartFile
    public CustomMultipartFile getSongFile() {
        if (songFile == null && songDataBytes != null) {
            songFile = new CustomMultipartFile(
                    songDataBytes,
                    "songFile", // Name - You can provide any name that makes sense in your context
                    "example.mp3", // Original Filename - Replace with the actual filename
                    "audio/mpeg" // Content Type - Replace with the actual content type
            );

        }
        return songFile;
    }

    // Setter for CustomMultipartFile, convert CustomMultipartFile to byte[]
    public void setSongFile(CustomMultipartFile songFile) {
        this.songFile = songFile;

        try {
            this.songDataBytes = songFile.getBytes();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read song file data", e);
        }
    }

    public void setSongFileData(byte[] songDataBytes) {
        this.songDataBytes = songDataBytes;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        String string = songTitle + " " + artistName + " " + songCollectionType;
        if (songCollection != null) {
            string += " is in collection " + songCollection.toString() + ".";
        } else {
            string += " has no collection.";
        }
        return string;
    }
}
