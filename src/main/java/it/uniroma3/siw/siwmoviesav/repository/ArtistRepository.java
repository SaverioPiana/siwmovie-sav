package it.uniroma3.siw.siwmoviesav.repository;

import it.uniroma3.siw.siwmoviesav.model.Artist;
import org.springframework.data.repository.CrudRepository;

public interface ArtistRepository extends CrudRepository<Artist, Long> {
    public boolean existsByNameAndSurname(String name, String surname);
}
