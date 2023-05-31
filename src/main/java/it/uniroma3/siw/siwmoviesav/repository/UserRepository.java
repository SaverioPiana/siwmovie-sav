package it.uniroma3.siw.siwmoviesav.repository;

import it.uniroma3.siw.siwmoviesav.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
}
