package it.uniroma3.siw.siwmoviesav.service;

import it.uniroma3.siw.siwmoviesav.model.Movie;
import it.uniroma3.siw.siwmoviesav.repository.MovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieService {
    @Autowired
    protected MovieRepository movieRepository;

    @Transactional
    public void createNewMovie(Movie movie){
        movieRepository.save(movie);
    }
    @Transactional
    public void updateMovie(Movie movie){
        movieRepository.save(movie);
    }
    public Movie findById(Long id){
        return movieRepository.findById(id).orElse(null);
    }
    public Iterable<Movie> findAll(){
        return movieRepository.findAll();
    }
}
