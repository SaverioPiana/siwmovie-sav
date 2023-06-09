package it.uniroma3.siw.siwmoviesav.service;

import it.uniroma3.siw.siwmoviesav.model.Artist;
import it.uniroma3.siw.siwmoviesav.model.Movie;
import it.uniroma3.siw.siwmoviesav.repository.ArtistRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArtistService {
    @Autowired
    ArtistRepository artistRepository;

    @Transactional
    public void save(Artist artist){
        artistRepository.save(artist);
    }

    public boolean alreadyExists(Artist artist){
        return artistRepository.existsByNameAndSurname(artist.getName(), artist.getSurname());
    }
    public Artist findById(Long id){
        return artistRepository.findById(id).orElse(null);
    }
    public Iterable<Artist> findAll(){
        return artistRepository.findAll();
    }
    public Iterable<Artist> findDirectorsToSel(Movie movie){
        return artistRepository.findAllByDirectedMoviesIsNotContaining(movie);
    }
    public Iterable<Artist> findActorsToSel(Movie movie){
        return artistRepository.findAllByStarredMoviesIsNotContaining(movie);
    }
}
