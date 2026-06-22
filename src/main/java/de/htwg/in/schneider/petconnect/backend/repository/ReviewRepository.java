package de.htwg.in.schneider.petconnect.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import de.htwg.in.schneider.petconnect.backend.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByReviewedUserId(Long userId);
    List<Review> findByReviewerId(Long userId);
    
}
