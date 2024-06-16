package Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.CustomMultipartFile;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.SongCollection;

import java.time.LocalDateTime;

public class SongDto {
    private Long id;
    private byte[] songData;
//    private CustomMultipartFile songFile;
    private String songTitle;
    private String artistName;
    private String fileName;
    private Long fileSize;
    private LocalDateTime uploadTime;
    private SongCollection songCollection;
    private String songUrl;

    public SongDto() {
    }

    public SongDto(Long id, byte[] songData, CustomMultipartFile songFile, String songTitle, String artistName, String fileName, Long fileSize, LocalDateTime uploadTime, SongCollection songCollection, String songUrl) {
        this.id = id;
//        this.songData = songData;
//        this.songFile = songFile;
        this.songTitle = songTitle;
        this.artistName = artistName;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.uploadTime = uploadTime;
        this.songCollection = songCollection;
        this.songUrl = songUrl;
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
//
//    public CustomMultipartFile getSongFile() {
//        return songFile;
//    }
//
//    public void setSongFile(CustomMultipartFile songFile) {
//        this.songFile = songFile;
//    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
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

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }
}
