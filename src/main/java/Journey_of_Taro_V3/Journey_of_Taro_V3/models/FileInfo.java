package Journey_of_Taro_V3.Journey_of_Taro_V3.models;

import java.util.Date;

public class FileInfo {

    private String fileName;
    private String contentType;
    private boolean isEmpty;
    private boolean isReadable;
    private byte[] fileData;
    private String fileUploadStatus;

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
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public boolean isReadable() {
        return isReadable;
    }

    public void setReadable(boolean readable) {
        isReadable = readable;
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

    public String getCurrentTimeStamp() {
        return String.valueOf(new Date().getTime());
    }


}
