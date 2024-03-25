package Journey_of_Taro_V3.Journey_of_Taro_V3.models.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.BadRequestException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.CustomMultipartFile;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.users.User;
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
    private byte[] songData; // store byte[] data

    @Transient
    private CustomMultipartFile songFile; // transient, not persisted in the database

    private String songTitle;

    @ManyToOne
    @JoinColumn(name = "artist_username", referencedColumnName = "username")
    private User artistName;

    private String fileName;
    private Long fileSize;
    private LocalDateTime uploadTime;

    @ManyToOne
    @JoinColumn(name = "collection_id")
    private SongCollection songCollection;

    @Enumerated(EnumType.STRING)
    private SongCollectionType songCollectionType;

    public Song() {
    }

    public Song(String songTitle, CustomMultipartFile songFile, User artistName, SongCollectionType collectionType) {
        if (songTitle == null || songTitle.trim().isEmpty()) {
            throw new BadRequestException("Song title cannot be null or empty");
        }

        if (songFile == null || songFile.isEmpty()) {
            throw new BadRequestException("Please choose an mp3 Audio file");
        }

        this.songTitle = songTitle;
        this.songFile = songFile;
        this.artistName = artistName;

        // Convert CustomMultipartFile to byte[]
        try {
            this.songData = songFile.getBytes();
            this.fileName = songFile.getOriginalFilename();
            this.fileSize = songFile.getSize();
            this.uploadTime = LocalDateTime.now();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read song file data", e);
        }
    }

//    public Song(String songTitle, CustomMultipartFile songFile, User artistName, SongCollectionType collectionType) {
//        if (songTitle == null || songTitle.trim().isEmpty()) {
//            throw new BadRequestException("Song title cannot be null or empty");
//        }
//
//        if (songFile == null || songFile.isEmpty()) {
//            throw new BadRequestException("Please choose an mp3 Audio file");
//        }
//
////        if (collectionType == null) {
////            throw new BadRequestException("Please provide a collection type");
////        }
////
////        if (artistName == null || artistName.getUsername() == null || artistName.getUsername().trim().isEmpty()) {
////            throw new BadRequestException("Please provide an artist name");
////        }
//
//        this.songTitle = songTitle;
//        this.artistName = artistName; // Assign the User object
////        this.songCollectionType = collectionType;
//
//        // Convert CustomMultipartFile to byte[]
//        try {
//            this.songData = songFile.getBytes();
//            this.fileName = songFile.getOriginalFilename();
//            this.fileSize = songFile.getSize();
//            this.uploadTime = LocalDateTime.now();
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to SongReadController song file data", e);
//        }
//
//        //
//        this.songFile = songFile;
//    }

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

    public User getArtistName() {
        return artistName;
    }

    public void setArtistName(User artistName) {
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

    public void setSongCollection(SongCollection songCollection) {
        this.songCollection = songCollection;
    }

    public SongCollection getSongCollection() {
        return songCollection;
    }

    // Song to String
    @Override
    public String toString() {
        String string = songTitle + " " + artistName.getUsername() + " " + songCollectionType; // Use getUsername to get the artist's username
//        if (songCollection != null) {
//            string += " is in collection " + songCollection.toString() + ".";
//        } else {
//            string += " has no collection.";
//        }
        return string;
    }
}
