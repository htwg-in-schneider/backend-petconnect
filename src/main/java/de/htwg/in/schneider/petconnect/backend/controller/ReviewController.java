package de.htwg.in.schneider.petconnect.backend.controller;

import de.htwg.in.schneider.petconnect.backend.model.Ausschreibung;
import de.htwg.in.schneider.petconnect.backend.model.Review;

import de.htwg.in.schneider.petconnect.backend.repository.AusschreibungRepository;
import de.htwg.in.schneider.petconnect.backend.repository.ReviewRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/review")

@CrossOrigin(origins = "*")
public class ReviewController {
    private static final Logger LOG = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private AusschreibungRepository ausschreibungRepository;



    @GetMapping
    public List<Review> getAllReviews() {
        LOG.info("Fetching all reviews");
        List<Review> reviews = reviewRepository.findAll();
        LOG.info("Found {} reviews", reviews != null ? reviews.size() : 0);
        return reviews;
    }



    @GetMapping("/ausschreibung/{id}")
    public List<Review> getReviewsByAusschreibung(@PathVariable Long id) {
        LOG.info("Fetching reviews for ausschreibung id {}", id);
        List<Review> reviews = reviewRepository.findByAusschreibungId(id);
        LOG.info("Found {} reviews for ausschreibung {}", reviews != null ? reviews.size() : 0, id);
        return reviews;
    }

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        Long ausschreibungId = null;
        if (review != null && review.getAusschreibung() != null) {
            ausschreibungId = review.getAusschreibung().getId();
        }
        LOG.info("Attempting to create review for ausschreibung id {}", ausschreibungId);
        
        if (review == null) {
            LOG.warn("Review payload is null");
            return ResponseEntity.badRequest().build();
        }
        
        int stars = review.getStars();
        if (stars < 1 || stars > 5) {
            LOG.warn("Review stars out of bounds: {}", stars);
            return ResponseEntity.badRequest().build();
        }

        if (review.getAusschreibung() == null ||
            review.getAusschreibung().getId() == null) {
            LOG.warn("Review creation failed: Ausschreibung or Ausschreibung ID is null");
            return ResponseEntity.badRequest().build();

        }

        Ausschreibung ausschreibung =ausschreibungRepository.findById(
                        review.getAusschreibung().getId()).orElse(null);

        if (ausschreibung == null) {
            LOG.warn("Attempted to create a review for non-existing ausschreibung with id {}", review.getAusschreibung().getId());
            return ResponseEntity.badRequest().build();
        }

        review.setAusschreibung(ausschreibung);
        Review savedReview =reviewRepository.save(review);
        LOG.info("Created review with id {}", savedReview.getId()); 
        return ResponseEntity.ok(savedReview);

    }



    @DeleteMapping("/{id}")

    public ResponseEntity<Object> deleteReview(
            @PathVariable Long id
    ) {

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