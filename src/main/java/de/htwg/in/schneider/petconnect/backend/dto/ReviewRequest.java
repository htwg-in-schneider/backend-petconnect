package de.htwg.in.schneider.petconnect.backend.dto;

public class ReviewRequest {
    private Long reviewedUserId;

    private Long ausschreibungId;

    private int stars;

    private String text;

    public Long getReviewedUserId() {
        return reviewedUserId;
    }

    public void setReviewedUserId(Long reviewedUserId) {
        this.reviewedUserId = reviewedUserId;
    }

    public Long getAusschreibungId() {
        return ausschreibungId;
    }

    public void setAusschreibungId(Long ausschreibungId) {
        this.ausschreibungId = ausschreibungId;
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
    
}
