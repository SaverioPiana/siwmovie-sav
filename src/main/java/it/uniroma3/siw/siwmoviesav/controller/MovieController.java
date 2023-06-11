package it.uniroma3.siw.siwmoviesav.controller;

import it.uniroma3.siw.siwmoviesav.controller.validator.MovieValidator;
import it.uniroma3.siw.siwmoviesav.model.Artist;
import it.uniroma3.siw.siwmoviesav.model.Movie;
import it.uniroma3.siw.siwmoviesav.service.ArtistService;
import it.uniroma3.siw.siwmoviesav.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class MovieController {
    @Autowired
    MovieService movieService;
    @Autowired
    ArtistService artistService;
    @Autowired
    MovieValidator movieValidator;

    @GetMapping("/formNewMovie")
    public String formNewMovie(Model model){
        Movie movie =  new Movie();
        model.addAttribute("movie", movie);
        return "formNewMovie";
    }

    @PostMapping("/movie")
    public String newMovie(@Valid @ModelAttribute("movie") Movie movie, BindingResult bindingResult, Model model){
        movieValidator.validate(movie, bindingResult);
        if(!bindingResult.hasErrors()){
            this.movieService.createNewMovie(movie);
            model.addAttribute("movie", movie);
            return "movie";
        }else{
            return "formNewMovie";
        }
    }
    @GetMapping("/movie")
    public String showMovies(Model model){
        model.addAttribute("movies", this.movieService.findAll());
        return "movies";
    }
    @GetMapping("/movie/{id}")
    public String getMovie(@PathVariable("id") Long id, Model model){
        Movie movie = movieService.findById(id);
        if(movie == null) return "/errors/movieNotFoundError";

        model.addAttribute("movie", movie);
        return "movie";
    }
    @GetMapping("/formSearchMovies")
    public String formSearchMovies(Model model){
        return "formSearchMovies";
    }
    @PostMapping("/searchMovies")
    public String searchMovies(Model model, @RequestParam Integer year){
        model.addAttribute("movies", this.movieService.findByYear(year));
        return "foundMovies";
    }

    @GetMapping("/selectDirectorToMovie/{id}")
    public String selectDirectorToMovie(@PathVariable("id") Long id, Model model){
        Movie movie = movieService.findById(id);
        if(movie == null) return "/errors/movieNotFoundError";

        model.addAttribute("movie", movie);
        model.addAttribute("artists", artistService.findAll());
        return "selectDirectorToMovie";
    }
    @GetMapping("/setDirectorToMovie/{movie_id}/{artist_id}")
    public String addDirectorToMovie(@PathVariable("movie_id") Long movie_id,
                                     @PathVariable("artist_id") Long artist_id,
                                     Model model){
        Artist director = artistService.findById(artist_id);
        if(director == null) return "/errors/artistNotFoundError";

        Movie movie = movieService.findById(movie_id);
        if(movie == null) return "/errors/movieNotFoundError";

        movie.setDirector(director);
        movieService.save(movie);
        model.addAttribute("movie", movie);
        return "movie";
    }
    @GetMapping("/selectActorsToMovie/{id}")
    public String selectActorsToMovie(@PathVariable("id") Long id, Model model){
        Movie movie = movieService.findById(id);
        if(movie == null) return "/errors/movieNotFoundError";

        model.addAttribute("artists", artistService.findAll());
        return "selectActorsToMovie";
    }

    @GetMapping("/addActorToMovie/{movie_id}/{artist_id}")
    public String addActorToMovie(@PathVariable("movie_id") Long movie_id,
                                     @PathVariable("artist_id") Long artist_id,
                                     Model model){
        Artist actor = artistService.findById(artist_id);
        if(actor == null) return "/errors/artistNotFoundError";

        Movie movie = movieService.findById(movie_id);
        if(movie == null) return "/errors/movieNotFoundError";

        movie.getActors().add(actor);
        movieService.save(movie);
        model.addAttribute("movie", movie);
        return selectActorsToMovie(movie_id, model);
    }
    @GetMapping("/removeActorFromMovie/{movie_id}/{artist_id}")
    public String removeActorFromMovie(@PathVariable("movie_id") Long movie_id,
                                  @PathVariable("artist_id") Long artist_id,
                                  Model model){
        Artist actor = artistService.findById(artist_id);
        if(actor == null) return "/errors/artistNotFoundError";

        Movie movie = movieService.findById(movie_id);
        if(movie == null) return "/errors/movieNotFoundError";

        movie.getActors().remove(actor);
        movieService.save(movie);
        model.addAttribute("movie", movie);
        return selectActorsToMovie(movie_id, model);
    }
}
