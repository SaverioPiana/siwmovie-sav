package it.uniroma3.siw.siwmoviesav.controller.validator;

import it.uniroma3.siw.siwmoviesav.model.Artist;
import it.uniroma3.siw.siwmoviesav.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class ArtistValidator implements Validator {
    @Autowired
    private ArtistService artistService;
    @Override
    public boolean supports(Class<?> clazz) {
        return Artist.class.equals(clazz);
    }
    @Override
    public void validate(Object target, Errors errors) {
        Artist artist = (Artist) target;
        if(artistService.alreadyExists(artist))
            errors.reject("artist.duplicate");
    }
}
