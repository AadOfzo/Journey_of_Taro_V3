package Journey_of_Taro_V3.Journey_of_Taro_V3.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue
    Long id;
    private String songtitle;
    private String artistname;
    private Boolean isfavorited;

}
