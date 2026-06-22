package de.htwg.in.schneider.petconnect.backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ReviewRequest {
    @NotNull
    private Long reviewedUserId;

    @NotNull
    private Long ausschreibungId;

    @Min(value = 1, message = "Mindestens 1 Stern erforderlich")
    @Max(value = 5, message = "Maximal 5 Sterne erlaubt")
    private int stars;
    
    @NotBlank(message = "Kommentar darf nicht leer sein")
    @Size(min = 3, max = 1000, message = "Kommentar muss zwischen 3 und 1000 Zeichen lang sein")
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
