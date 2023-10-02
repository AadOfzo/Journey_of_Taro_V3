package Journey_of_Taro_V3.Journey_of_Taro_V3.services;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.Playlist;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.PlaylistRepository;
import org.springframework.stereotype.Service;

@Service
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;

    public PlaylistServiceImpl(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    @Override
    public void setPlaylistVisibility(Long playlistId, boolean isPublic) {
        // Add validation and error handling if necessary
        Playlist playlist = playlistRepository.findById(playlistId).orElse(null);
        if (playlist != null) {
            playlist.setIsPublic(isPublic);
            playlistRepository.save(playlist);
        }
    }

    @Override
    public Playlist getPlaylistById(Long playlistId) {
        // Implement logic to retrieve playlist by ID from repository
        return playlistRepository.findById(playlistId).orElse(null);
    }
}
