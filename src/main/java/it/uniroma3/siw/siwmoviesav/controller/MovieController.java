package it.uniroma3.siw.siwmoviesav.controller;

import it.uniroma3.siw.siwmoviesav.model.Movie;
import it.uniroma3.siw.siwmoviesav.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MovieController {
    @Autowired
    MovieRepository movieRepository;

    @GetMapping("/formNewMovie")
    public String formNewMovie(Model model){
        model.addAttribute("movie", new Movie());
        return "formNewMovie";
    }

    @PostMapping("/movie")
    public String newMovie(@ModelAttribute("movie") Movie movie, Model model){
        if(!movieRepository.existsByTitleAndYear(movie.getTitle(), movie.getYear())){
            this.movieRepository.save(movie);
            model.addAttribute("movie", movie);
            return "movie";
        }else{
            model.addAttribute("messaggioErrore", "Questo film esiste gia'");
            return "formNewMovie";
        }
    }
    @GetMapping("/movie")
    public String showMovies(Model model){
        model.addAttribute("movies", this.movieRepository.findAll());
        return "movies";
    }
    @GetMapping("/movie/{id}")
    public String getMovie(@PathVariable("id") Long id, Model model){
        model.addAttribute("movie", this.movieRepository.findById(id).get());
        return "movie";
    }
    @GetMapping("/formSearchMovies")
    public String formSearchMovies(Model model){
        return "formSearchMovies.html";
    }
    @PostMapping("/searchMovies")
    public String searchMovies(Model model, @RequestParam Integer year){
        model.addAttribute("movies", this.movieRepository.findByYear(year));
        return "foundMovies";
    }
}
