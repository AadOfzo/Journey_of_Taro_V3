package Journey_of_Taro_V3.Journey_of_Taro_V3.models;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.music.SongInputDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.music.Song;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class CustomMultipartFile implements MultipartFile {
    private final byte[] input;
    private final String name;
    private final String originalFilename;
    private final String contentType;

    public CustomMultipartFile(String originalFilename, String contentType, byte[] inputArray) {
        this.input = inputArray.clone();
        this.name = "file"; // You can set a default name if needed
        this.originalFilename = originalFilename;
        this.contentType = contentType;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getOriginalFilename() {
        return this.originalFilename;
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public boolean isEmpty() {
        return input == null || input.length == 0;
    }

    @Override
    public long getSize() {
        return input.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return input;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(input);
    }

    @Override
    public void transferTo(File destination) throws IOException, IllegalStateException {
        try (FileOutputStream fos = new FileOutputStream(destination)) {
            fos.write(input);
        }
    }
    // Convert Image to Entity

    // Convert Song to Entity
    private Song convertFileToSongEntity(SongInputDto inputDto) throws IOException {
        Song song = new Song();
        song.setSongTitle(inputDto.getSongTitle());

        // Get the byte array from the SongInputDto
        byte[] songFileBytes = inputDto.getSongFile().getBytes();

        // Use the appropriate values for the CustomMultipartFile constructor
        CustomMultipartFile songFile = new CustomMultipartFile(
                inputDto.getSongTitle(), // Original filename
                "audio/mpeg", // Content type - Replace with the actual content type
                songFileBytes
        );

        song.setSongFile(songFile);

        return song;
    }


}
