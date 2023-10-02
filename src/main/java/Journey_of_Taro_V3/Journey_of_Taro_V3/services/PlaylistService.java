package Journey_of_Taro_V3.Journey_of_Taro_V3.services;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.Playlist;

public interface PlaylistService {
    void setPlaylistVisibility(Long playlistId, boolean isPublic);
    Playlist getPlaylistById(Long playlistId);
}
