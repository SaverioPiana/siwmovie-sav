package it.uniroma3.siw.siwmoviesav.controller;

import it.uniroma3.siw.siwmoviesav.controller.validator.ArtistValidator;
import it.uniroma3.siw.siwmoviesav.model.Artist;
import it.uniroma3.siw.siwmoviesav.model.Movie;
import it.uniroma3.siw.siwmoviesav.repository.ArtistRepository;
import it.uniroma3.siw.siwmoviesav.service.ArtistService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArtistController {
    @Autowired
    private ArtistValidator artistValidator;
    @Autowired
    private ArtistService artistService;


    @GetMapping("/formNewArtist")
    public String formNewArtist(Model model){
        model.addAttribute("artist", new Artist());
        return "formNewArtist";
    }
    @PostMapping("/artist")
    public String newArtist(@Valid @ModelAttribute("artist") Artist artist, BindingResult bindingResult, Model model){
        artistValidator.validate(artist, bindingResult);
        if(!bindingResult.hasErrors()){
            this.artistService.createNewArtist(artist);
            model.addAttribute("movie", artist);
            return "artist";
        }else{
            return "formNewArtist";
        }
    }
    @GetMapping("/artist")
    public String showArtists(Model model){
        model.addAttribute("artists", artistService.findAll());
        return "artists";
    }

    @GetMapping("/artist/{id}")
    public String getArtist(@PathVariable("id") Long id, Model model){
        model.addAttribute("artist", artistService.findById(id));
        return "artist";
    }
}
