package Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions;

public class SongTitleNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public SongTitleNotFoundException(String songtitle) {
        super("Cannot find song " + songtitle);
    }
}
