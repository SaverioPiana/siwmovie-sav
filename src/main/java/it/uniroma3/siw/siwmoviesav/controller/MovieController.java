package it.uniroma3.siw.siwmoviesav.controller;

import it.uniroma3.siw.siwmoviesav.controller.util.FileUploadUtil;
import it.uniroma3.siw.siwmoviesav.controller.validator.MovieValidator;
import it.uniroma3.siw.siwmoviesav.model.Artist;
import it.uniroma3.siw.siwmoviesav.model.Movie;
import it.uniroma3.siw.siwmoviesav.service.ArtistService;
import it.uniroma3.siw.siwmoviesav.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Controller
public class MovieController {
    @Autowired
    MovieService movieService;
    @Autowired
    ArtistService artistService;
    @Autowired
    MovieValidator movieValidator;

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
    //############### ADMIN #################
    @GetMapping("/admin/movie")
    public String showMoviesAdmin(Model model){
        model.addAttribute("movies", this.movieService.findAll());
        return "admin/moviesAdmin";
    }
    @PostMapping("/admin/movie")
    public String newMovie(@Valid @ModelAttribute("movie") Movie movie, BindingResult bindingResult, Model model){
        movieValidator.validate(movie, bindingResult);
        if(!bindingResult.hasErrors()){
            this.movieService.createNewMovie(movie);
            model.addAttribute("movie", movie);
            return "admin/movieAdmin";
        }else{
            return "admin/formNewMovie";
        }
    }
    @GetMapping("/admin/movie/{id}")
    public String getMovieAdmin(@PathVariable("id") Long id, Model model){
        Movie movie = movieService.findById(id);
        if(movie == null) return "errors/movieNotFoundError";

        model.addAttribute("movie", movie);
        return "admin/movieAdmin";
    }
    @GetMapping("/admin/formNewMovie")
    public String formNewMovie(Model model){
        Movie movie =  new Movie();
        model.addAttribute("movie", movie);
        return "admin/formNewMovie";
    }
    @GetMapping("/admin/selectDirectorToMovie/{id}")
    public String selectDirectorToMovie(@PathVariable("id") Long id, Model model){
        Movie movie = movieService.findById(id);
        if(movie == null) return "errors/movieNotFoundError";

        model.addAttribute("movie", movie);
        model.addAttribute("artists", artistService.findDirectorsToSel(movie));
        return "admin/selectDirectorToMovie";
    }
    @GetMapping("/admin/setDirectorToMovie/{movie_id}/{artist_id}")
    public String addDirectorToMovie(@PathVariable("movie_id") Long movie_id,
                                     @PathVariable("artist_id") Long artist_id,
                                     Model model){
        Artist director = artistService.findById(artist_id);
        if(director == null) return "errors/artistNotFoundError";

        Movie movie = movieService.findById(movie_id);
        if(movie == null) return "errors/movieNotFoundError";

        movie.setDirector(director);
        movieService.save(movie);
        model.addAttribute("movie", movie);
        return "admin/movieAdmin";
    }
    @GetMapping("/admin/selectActorsToMovie/{id}")
    public String selectActorsToMovie(@PathVariable("id") Long id, Model model){
        Movie movie = movieService.findById(id);
        if(movie == null) return "errors/movieNotFoundError";

        model.addAttribute("movie", movie);
        model.addAttribute("artists", artistService.findActorsToSel(movie));
        return "admin/selectActorsToMovie";
    }

    @GetMapping("/admin/addActorToMovie/{movie_id}/{artist_id}")
    public String addActorToMovie(@PathVariable("movie_id") Long movie_id,
                                     @PathVariable("artist_id") Long artist_id,
                                     Model model){
        Artist actor = artistService.findById(artist_id);
        if(actor == null) return "errors/artistNotFoundError";

        Movie movie = movieService.findById(movie_id);
        if(movie == null) return "errors/movieNotFoundError";

        movie.getActors().add(actor);
        movieService.save(movie);
        model.addAttribute("movie", movie);
        return selectActorsToMovie(movie_id, model);
    }
    @GetMapping("/admin/removeActorFromMovie/{movie_id}/{artist_id}")
    public String removeActorFromMovie(@PathVariable("movie_id") Long movie_id,
                                  @PathVariable("artist_id") Long artist_id,
                                  Model model){
        Artist actor = artistService.findById(artist_id);
        if(actor == null) return "errors/artistNotFoundError";

        Movie movie = movieService.findById(movie_id);
        if(movie == null) return "errors/movieNotFoundError";

        movie.getActors().remove(actor);
        movieService.save(movie);
        model.addAttribute("movie", movie);
        return selectActorsToMovie(movie_id, model);
    }
    @PostMapping("/admin/saveMovieImage/{id}")
    public String saveMovieImage(@PathVariable("id") Long id,
                                  @RequestParam("image") MultipartFile multipartFile, Model model) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        Movie movie = movieService.findById(id);
        if(movie == null) return "errors/movieNotFoundError";

        movie.setPicFilename(fileName);
        movieService.save(movie);
        String uploadDir = "src/main/upload/images/movie_pics/" + movie.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        return "redirect:/admin/movie/"+ id;
    }
}
