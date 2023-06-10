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
        Movie movie = movieService.findById(movie_id);
        if(movie == null) return "/errors/movieNotFoundError";

        Review review = new Review(movie, userService.getCurrentUser());
        model.addAttribute("review", review);
        return "registered/formNewReview";
    }
    @PostMapping("/registered/review")
    public String newMovie(@Valid @ModelAttribute("review") Review review, BindingResult bindingResult, Model model){
        if(!bindingResult.hasErrors()){
            this.reviewService.createNewReview(review);
            model.addAttribute("review", review);
            return "movie/" + review.getReviewedMovie().getId();
        }else{
            return "/registered/formNewReview";
        }
    }
}
