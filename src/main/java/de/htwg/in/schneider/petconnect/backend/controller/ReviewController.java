package de.htwg.in.schneider.petconnect.backend.controller;

import de.htwg.in.schneider.petconnect.backend.dto.ReviewRequest;
import de.htwg.in.schneider.petconnect.backend.model.Ausschreibung;
import de.htwg.in.schneider.petconnect.backend.model.Review;
import de.htwg.in.schneider.petconnect.backend.model.User;
import de.htwg.in.schneider.petconnect.backend.repository.UserRepository;
import de.htwg.in.schneider.petconnect.backend.repository.AusschreibungRepository;

import de.htwg.in.schneider.petconnect.backend.repository.ReviewRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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
    private UserRepository userRepository;

    @Autowired
    private AusschreibungRepository ausschreibungRepository;
    

    @GetMapping
    public List<Review> getAllReviews() {
       return reviewRepository.findAll();
    }


    @PostMapping
    public ResponseEntity<Review> createReview(@AuthenticationPrincipal Jwt jwt, @RequestBody ReviewRequest dto) {
        LOG.info("Creating review");
     // Eingeloggten User holen    
    User reviewer = userRepository
    .findByOauthId(jwt.getSubject())
    .orElseThrow();

    // Bewerteten User holen
    User reviewedUser = userRepository
            .findById(dto.getReviewedUserId())
            .orElseThrow();

     // Ausschreibung holen
    Ausschreibung ausschreibung = ausschreibungRepository
    .findById(dto.getAusschreibungId())
            .orElseThrow();

    //Sterne prüfen
        if(dto.getStars()<1 || dto.getStars()> 5){
            return ResponseEntity.badRequest().build();
        }

    // Betreuung muss abgeschlossen sein
    if (ausschreibung.getStatus()
            != Ausschreibung.AusschreibungStatus.ABGESCHLOSSEN) {
        return ResponseEntity.badRequest().build();
    }
    User owner = ausschreibung.getOwner();
    User betreuer = ausschreibung.getBetreuer();

     // Nur Besitzer und Betreuer dürfen bewerten
    boolean ownerBewertetBetreuer =
            reviewer.getId().equals(owner.getId())
            && reviewedUser.getId().equals(betreuer.getId());

    boolean betreuerBewertetOwner =
            reviewer.getId().equals(betreuer.getId())
            && reviewedUser.getId().equals(owner.getId());

    if (!ownerBewertetBetreuer && !betreuerBewertetOwner) {
        return ResponseEntity.status(403).build();
    }

    // Doppelte Bewertung verhindern
    boolean bereitsBewertet =reviewRepository.existsByReviewerIdAndAusschreibungId(
                    reviewer.getId(),
                    ausschreibung.getId());

    if (bereitsBewertet) {
        return ResponseEntity.badRequest().build();
    }

    // Review anlegen
    Review review = new Review();
    review.setReviewer(reviewer);
    review.setReviewedUser(reviewedUser);
    review.setStars(dto.getStars());
    review.setText(dto.getText());
    review.setAusschreibung(ausschreibung);

    Review savedReview = reviewRepository.save(review);
    return ResponseEntity.ok(savedReview);
}

        
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteReview(@PathVariable Long id) {

    Review review =reviewRepository.findById(id).orElse(null);
    if (review == null) {
    return ResponseEntity.notFound().build();
    }
    reviewRepository.delete(review);
    return ResponseEntity.noContent().build();
    }

@GetMapping("/user/{userId}")
public List<Review> getReviewsForUser(@PathVariable Long userId) {
    return reviewRepository.findByReviewedUserId(userId);
}

@GetMapping("/mine")
public ResponseEntity<List<Review>> getMyReviews(@AuthenticationPrincipal Jwt jwt) {
    User currentUser = userRepository.findByOauthId(jwt.getSubject()).orElseThrow();
    List<Review> reviews = reviewRepository.findByReviewedUserId(currentUser.getId());
    return ResponseEntity.ok(reviews);
}

@GetMapping("/user/{userId}/average")
public ResponseEntity<Double> getAverageRating(@PathVariable Long userId) {
    List<Review> reviews = reviewRepository.findByReviewedUserId(userId);
    if (reviews.isEmpty()) {
        return ResponseEntity.ok(0.0);
    }
    double average = reviews.stream()
            .mapToInt(Review::getStars)
            .average()
            .orElse(0.0);
    return ResponseEntity.ok(average);
}

@GetMapping("/ausschreibung/{ausschreibungId}")
public List<Review> getReviewsByAusschreibung(@PathVariable Long ausschreibungId) {
    return reviewRepository.findByAusschreibungId(ausschreibungId);
}


}

