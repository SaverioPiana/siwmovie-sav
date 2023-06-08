package it.uniroma3.siw.siwmoviesav.service;

import it.uniroma3.siw.siwmoviesav.model.Artist;
import it.uniroma3.siw.siwmoviesav.repository.ArtistRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArtistService {
    @Autowired
    ArtistRepository artistRepository;

    @Transactional
    public void createNewArtist(Artist artist){
        artistRepository.save(artist);
    }
    @Transactional
    public void updateArtist(Artist artist){
        artistRepository.save(artist);
    }
    public Artist findById(Long id){
        return artistRepository.findById(id).orElse(null);
    }
    public Iterable<Artist> findAll(){
        return artistRepository.findAll();
    }
}
