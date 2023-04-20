package Journey_of_Taro_V3.Journey_of_Taro_V3.dtos;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

public class SongInputDto {

    @NotNull(message = "Songtitle is required")
    private String songtitle;

    @AssertTrue(message = "All favorited songs")
    private Boolean isfavorited;

    public SongInputDto(String songtitle, Boolean isfavorited) {
        this.songtitle = songtitle;
        this.isfavorited = isfavorited;
    }

    public String getSongtitle() {
        return songtitle;
    }

    public void setSongtitle(String songtitle) {
        this.songtitle = songtitle;
    }

    public Boolean getIsfavorited() {
        return isfavorited;
    }

    public void setIsfavorited(Boolean isfavorited) {
        this.isfavorited = isfavorited;
    }
}
