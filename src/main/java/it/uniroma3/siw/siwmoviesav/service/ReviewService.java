package it.uniroma3.siw.siwmoviesav.service;

import it.uniroma3.siw.siwmoviesav.model.Review;
import it.uniroma3.siw.siwmoviesav.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Transactional
    public void createNewReview(Review review){
        reviewRepository.save(review);
    }
}
