package Journey_of_Taro_V3.Journey_of_Taro_V3.services;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.Playlist;

//Als dit een class is werkt de playlist implementatie niet meer
public interface PlaylistService {
    void setPlaylistVisibility(Long playlistId, boolean isPublic);
    Playlist getPlaylistById(Long playlistId);
}
