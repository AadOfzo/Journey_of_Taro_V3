package Journey_of_Taro_V3.Journey_of_Taro_V3.services;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.MusicCollection;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.MusicCollectionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicCollectionService {
    private final MusicCollectionRepository musicCollectionRepository;
    private MusicCollectionRepository repository;

    public MusicCollectionService(MusicCollectionRepository musicCollectionRepository) {
        this.musicCollectionRepository = musicCollectionRepository;
    }

    public void setName(Long id, String name) {
        MusicCollection musicCollection = musicCollectionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("MusicCollection not found"));
        musicCollection.setMusicCollectionName(name);
        musicCollectionRepository.save(musicCollection);
    }

    public String getName(Long id) {
        MusicCollection musicCollection = musicCollectionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("MusicCollection not found"));
        return musicCollection.getMusicCollectionName();
    }
    public List<MusicCollection> getAll() {
        return repository.findAll();
    }

    public MusicCollection getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("MusicCollection not found with id: " + id));
    }

    public MusicCollection create(MusicCollection musicCollection) {
        return repository.save(musicCollection);
    }

    public MusicCollection update(Long id, MusicCollection musicCollection) {
        MusicCollection existingMusicCollection = getById(id);
        existingMusicCollection.setMusicCollectionName(musicCollection.getMusicCollectionName());
        // set any other fields you want to update
        return repository.save(existingMusicCollection);
    }

    public void delete(Long id) {
        MusicCollection musicCollection = getById(id);
        repository.delete(musicCollection);
    }
}
