package Journey_of_Taro_V3.Journey_of_Taro_V3.models;

import java.time.LocalDateTime;

public class FileInfo {
    private String fileName;
    private String contentType;
    private boolean empty;
    private boolean readable;
    private byte[] fileData;
    private String fileUploadStatus;
    private String fileType; // Add fileType field
    private long fileSize;
    private LocalDateTime uploadTime;

    public FileInfo() {
        this.fileName = fileName;
        this.contentType = contentType;
        this.empty = empty;
        this.readable = readable;
        this.fileData = fileData;
        this.fileUploadStatus = fileUploadStatus;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.uploadTime = uploadTime;
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public boolean isReadable() {
        return readable;
    }

    public void setReadable(boolean readable) {
        this.readable = readable;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public String getFileUploadStatus() {
        return fileUploadStatus;
    }

    public void setFileUploadStatus(String fileUploadStatus) {
        this.fileUploadStatus = fileUploadStatus;
    }

    // Add setter for fileType
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }

}
