package it.uniroma3.siw.siwmoviesav.repository;

import it.uniroma3.siw.siwmoviesav.model.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, Long> {
}
