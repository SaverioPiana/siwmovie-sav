package it.uniroma3.siw.siwmoviesav.controller;

import it.uniroma3.siw.siwmoviesav.model.Artist;
import it.uniroma3.siw.siwmoviesav.model.Movie;
import it.uniroma3.siw.siwmoviesav.repository.ArtistRepository;
import it.uniroma3.siw.siwmoviesav.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MovieController {
    @Autowired
    MovieRepository movieRepository;

    @Autowired
    ArtistRepository artistRepository;

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

    @GetMapping("/selectDirectorToMovie/{id}")
    public String selectDirectorToMovie(@PathVariable("id") Long id, Model model){
        model.addAttribute("movie", movieRepository.findById(id).get());
        model.addAttribute("artists", artistRepository.findAll());
        return "selectDirectorToMovie";
    }
    @GetMapping("/setDirectorToMovie/{movie_id}/{artist_id}")
    public String addDirectorToMovie(@PathVariable("movie_id") Long movie_id,
                                     @PathVariable("artist_id") Long artist_id,
                                     Model model){
        Artist director = artistRepository.findById(artist_id).get();
        Movie movie = movieRepository.findById(movie_id).get();
        movie.setDirector(director);
        movieRepository.save(movie);
        model.addAttribute("movie", movie);
        return "movie";
    }
    @GetMapping("/selectActorsToMovie/{id}")
    public String selectActorsToMovie(@PathVariable("id") Long id, Model model){
        model.addAttribute("movie", movieRepository.findById(id).get());
        model.addAttribute("artists", artistRepository.findAll());
        return "selectActorsToMovie";
    }

    @GetMapping("/addActorToMovie/{movie_id}/{artist_id}")
    public String addActorToMovie(@PathVariable("movie_id") Long movie_id,
                                     @PathVariable("artist_id") Long artist_id,
                                     Model model){
        Artist actor = artistRepository.findById(artist_id).get();
        Movie movie = movieRepository.findById(movie_id).get();
        movie.getActors().add(actor);
        movieRepository.save(movie);
        model.addAttribute("movie", movie);
        return selectActorsToMovie(movie_id, model);
    }
    @GetMapping("/removeActorFromMovie/{movie_id}/{artist_id}")
    public String removeActorFromMovie(@PathVariable("movie_id") Long movie_id,
                                  @PathVariable("artist_id") Long artist_id,
                                  Model model){
        Artist actor = artistRepository.findById(artist_id).get();
        Movie movie = movieRepository.findById(movie_id).get();
        movie.getActors().remove(actor);
        movieRepository.save(movie);
        model.addAttribute("movie", movie);
        return selectActorsToMovie(movie_id, model);
    }
}
