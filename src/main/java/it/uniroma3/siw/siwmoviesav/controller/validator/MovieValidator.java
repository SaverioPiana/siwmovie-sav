package it.uniroma3.siw.siwmoviesav.controller.validator;

import it.uniroma3.siw.siwmoviesav.model.Movie;
import it.uniroma3.siw.siwmoviesav.repository.MovieRepository;
import it.uniroma3.siw.siwmoviesav.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class MovieValidator implements Validator {
    @Autowired
    private MovieService movieService;
    @Override
    public boolean supports(Class<?> clazz) {
        return Movie.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Movie movie = (Movie)target;
        if(movieService.alreadyExists(movie)){
            errors.reject("movie.duplicate");
        }
    }
}
