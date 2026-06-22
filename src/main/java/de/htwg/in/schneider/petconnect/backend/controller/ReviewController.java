package de.htwg.in.schneider.petconnect.backend.controller;

import de.htwg.in.schneider.petconnect.backend.model.Review;

import de.htwg.in.schneider.petconnect.backend.repository.ReviewRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.htwg.in.schneider.petconnect.backend.model.User;
import de.htwg.in.schneider.petconnect.backend.repository.UserRepository;

@RestController
@RequestMapping("/api/review")

@CrossOrigin(origins = "*")
public class ReviewController {
    private static final Logger LOG = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;
    

    @GetMapping
    public List<Review> getAllReviews() {
       return reviewRepository.findAll();
    }


    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        LOG.info("Creating review");
    //Sterne prüfen
        if(review.getStars()<1 || review.getStars()> 5){
            return ResponseEntity.badRequest().build();
        }
    // Reviewer & Bewerteter User prüfen
    if(review.getReviewer() == null || review.getReviewer().getId() == null){
    return ResponseEntity.badRequest().build();
    }
        if(review.getReviewedUser() == null || review.getReviewedUser().getId() == null){
        return ResponseEntity.badRequest().build();
    }
    //User laden aus der Datenbank
    User reviewer = userRepository.findById(review.getReviewer().getId()).orElse(null);
    User reviewedUser = userRepository.findById(review.getReviewedUser().getId()).orElse(null);

    if (reviewer == null || reviewedUser == null) {
        return ResponseEntity.badRequest().build();
    }

    review.setReviewer(reviewer);
    review.setReviewedUser(reviewedUser);

    Review savedReview = reviewRepository.save(review);

    return ResponseEntity.ok(savedReview);
}

        
    @DeleteMapping("/{id}")

    public ResponseEntity<Object> deleteReview(
            @PathVariable Long id) {

        Review review =
                reviewRepository.findById(id)
                        .orElse(null);

        if (review == null) {

            return ResponseEntity.notFound().build();

        }

        reviewRepository.delete(review);

        return ResponseEntity.noContent().build();

            }
        }

