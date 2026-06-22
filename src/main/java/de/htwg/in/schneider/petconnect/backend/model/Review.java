package de.htwg.in.schneider.petconnect.backend.model;
import de.htwg.in.schneider.petconnect.backend.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int stars;
    private String text;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="reviewer_id")
    private User reviewer;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="reviewed_user_id")
    private User reviewedUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getReviewer() {
        return reviewer;
    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }

    public User getReviewedUser() {
        return reviewedUser;
    }

    public void setReviewedUser(User reviewedUser) {
        this.reviewedUser = reviewedUser;
    }




}
