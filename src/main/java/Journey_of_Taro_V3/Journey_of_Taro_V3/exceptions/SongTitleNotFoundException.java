package Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions;

import java.io.Serial;

public class SongTitleNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    public SongTitleNotFoundException(String songTitle) {
        super("Cannot find song " + songTitle);
    }
}
