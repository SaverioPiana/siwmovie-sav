package it.uniroma3.siw.siwmoviesav.repository;

import it.uniroma3.siw.siwmoviesav.model.Artist;
import it.uniroma3.siw.siwmoviesav.model.Movie;
import org.springframework.data.repository.CrudRepository;

import javax.imageio.ImageTranscoder;

public interface ArtistRepository extends CrudRepository<Artist, Long> {
    public boolean existsByNameAndSurname(String name, String surname);
    public Iterable<Artist> findAllByDirectedMoviesIsNotContaining(Movie movie);
    public Iterable<Artist> findAllByStarredMoviesIsNotContaining(Movie movie);
}
