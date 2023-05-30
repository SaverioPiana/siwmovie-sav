package it.uniroma3.siw.siwmoviesav.controller;

import it.uniroma3.siw.siwmoviesav.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ArtistController {
    @Autowired
    private ArtistRepository artistRepository;

    @GetMapping("/artist")
    public String showArtists(Model model){
        model.addAttribute("artists", artistRepository.findAll());
        return "artists";
    }

    @GetMapping("/artist/{id}")
    public String getArtist(@PathVariable("id") Long id, Model model){
        model.addAttribute("artist", artistRepository.findById(id).get());
        return "artist";
    }
}
