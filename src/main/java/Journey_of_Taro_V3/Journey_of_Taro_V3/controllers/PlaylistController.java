package Journey_of_Taro_V3.Journey_of_Taro_V3.controllers;

import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.PlaylistDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.Playlist;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @PutMapping("/{playlistId}")
    public void setPlaylistVisibility(@PathVariable Long playlistId, @RequestParam boolean isPublic) {
        playlistService.setPlaylistVisibility(playlistId, isPublic);
    }

    // Add more endpoints for handling playlists as needed

    @GetMapping("/{playlistId}")
    public PlaylistDto getPlaylist(@PathVariable Long playlistId) {
        // Logic to fetch Playlist entity from service
        Playlist playlist = playlistService.getPlaylistById(playlistId);

        // Convert Playlist entity to PlaylistDto
        PlaylistDto playlistDto = convertToDto(playlist);

        return playlistDto;
    }

    private PlaylistDto convertToDto(Playlist playlist) {
        PlaylistDto playlistDto = new PlaylistDto();
        playlistDto.setId(playlist.getId());
        playlistDto.setName(playlist.getPlaylistName());

        return playlistDto;
    }
}
