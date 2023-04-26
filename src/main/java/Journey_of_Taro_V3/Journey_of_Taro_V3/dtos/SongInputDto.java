package Journey_of_Taro_V3.Journey_of_Taro_V3.dtos;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

public class SongInputDto {

    @NotNull(message = "Songtitle is required")
    private String songtitle;
    @NotNull(message = "Artist name is required")
    private String artistname;
    @AssertTrue(message = "All favorited songs")
    private Boolean isfavorited;

}
