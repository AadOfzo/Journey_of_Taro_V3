package Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions;

import java.io.Serial;

public class BadRequestException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException() {
        super();
    }
}
