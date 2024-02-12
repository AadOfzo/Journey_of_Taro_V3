package Journey_of_Taro_V3.Journey_of_Taro_V3.models.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.BadRequestException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.CustomMultipartFile;
import jakarta.persistence.*;

import java.io.IOException;
import java.time.LocalDateTime;

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
    private String fileName;
    private Long fileSize;
    private LocalDateTime uploadTime;

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
            this.fileName = songFile.getOriginalFilename();
            this.fileSize = songFile.getSize();
            this.uploadTime = LocalDateTime.now();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read song file data", e);
        }

        // Set the transient field
        this.songFile = songFile;
    }

    // Getters and setters for additional properties

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getSongDataBytes() {
        return songDataBytes;
    }

    public void setSongDataBytes(byte[] songDataBytes) {
        this.songDataBytes = songDataBytes;
    }

    public CustomMultipartFile getSongFile() {
        return songFile;
    }

    public void setSongFile(CustomMultipartFile songFile) {
        this.songFile = songFile;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
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
