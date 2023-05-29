package it.uniroma3.siw.siwmoviesav.repository;

import it.uniroma3.siw.siwmoviesav.model.News;
import org.springframework.data.repository.CrudRepository;

public interface NewsRepository extends CrudRepository<News, Long> {
}
