package it.uniroma3.siw.siwmoviesav.controller;

import it.uniroma3.siw.siwmoviesav.model.Movie;
import it.uniroma3.siw.siwmoviesav.model.Review;
import it.uniroma3.siw.siwmoviesav.model.User;
import it.uniroma3.siw.siwmoviesav.repository.ReviewRepository;
import it.uniroma3.siw.siwmoviesav.service.MovieService;
import it.uniroma3.siw.siwmoviesav.service.ReviewService;
import it.uniroma3.siw.siwmoviesav.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private UserService userService;
    @Autowired
    private MovieService movieService;

    @GetMapping("/registered/formNewReview/{movie_id}")
    public String formNewReview(@PathVariable("movie_id") Long movie_id,Model model){
        model.addAttribute("review", new Review());
        model.addAttribute("movie_id", movie_id);
        return "registered/formNewReview";
    }
    @PostMapping("/registered/review/{movie_id}")
    public String newMovie(@PathVariable("movie_id") Long movie_id,@Valid @ModelAttribute("review") Review review, BindingResult bindingResult, Model model){
        if(!userService.canReview(userService.getCurrentUser(), movieService.findById(movie_id))){
            return "errors/cannotCreateMoreReview";
        }
        if(!bindingResult.hasErrors()){
            Movie movie = movieService.findById(movie_id);
            if(movie == null) return "/errors/movieNotFoundError";

            review.setReviewedMovie(movie);
            review.setAuthor(userService.getCurrentUser());
            review.setCreationDateTime(LocalDateTime.now());
            this.reviewService.createNewReview(review);
            model.addAttribute("review", review);
            return "redirect:/movie/" + movie_id;
        }else{
            return "/registered/formNewReview";
        }
    }
    public boolean canReview(User user, Movie movie){
        for (Review review : user.getReviews()) {
            if(review.getReviewedMovie().equals(movie)) return false;
        }
        return true;
    }
}
