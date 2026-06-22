package de.htwg.in.schneider.petconnect.backend.model;
import de.htwg.in.schneider.petconnect.backend.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Min(value = 1, message = "Mindestens 1 Stern erforderlich")
    @Max(value = 5, message = "Maximal 5 Sterne erlaubt")
    @Column(nullable = false)
    private int stars;

    @NotBlank(message = "Kommentar darf nicht leer sein")
    @Size(min = 3, max = 1000, message = "Kommentar muss zwischen 3 und 1000 Zeichen lang sein")
    @Column(nullable = false)
    private String text;

    @NotNull        
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="reviewer_id", nullable = false)
    private User reviewer;

    @NotNull
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="reviewed_user_id", nullable = false)
    private User reviewedUser;

    @NotNull
    @ManyToOne
    @JoinColumn(name="ausschreibung_id", nullable = false)
    private Ausschreibung ausschreibung;

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

    public Ausschreibung getAusschreibung() {
    return ausschreibung;
    }

    public void setAusschreibung(Ausschreibung ausschreibung){
        this.ausschreibung=ausschreibung;
    }




}
