package de.htwg.in.schneider.petconnect.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Ausschreibung {
    public enum AusschreibungStatus {
    VERFUEGBAR,
    VERGEBEN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String petName;
    private int petAge;
    @NotBlank
    private String city;
    private String postalCode;
    @Enumerated(EnumType.STRING)
    @NotNull
    private AnimalType animalType;
    private String description;
    @NotNull
    private LocalDate dateFrom;
    @NotNull
    private LocalDate dateTo;
    @NotBlank
    private String compensation;
    private String imageUrl;

    @OneToMany(mappedBy = "ausschreibung", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Review> reviews;

    @ManyToOne
    private User owner;

    @Enumerated(EnumType.STRING)
    private AusschreibungStatus status = AusschreibungStatus.VERFUEGBAR;

    @OneToMany(mappedBy = "ausschreibung")
    @JsonIgnore
    private List<Betreuungsanfrage> anfragen;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public int getPetAge() {
        return petAge;
    }

    public void setPetAge(int petAge) {
        this.petAge = petAge;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }

    public void setAnimalType(AnimalType animalType) {
        this.animalType = animalType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public String getCompensation() {
        return compensation;
    }

    public void setCompensation(String compensation) {
        this.compensation = compensation;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void addReview(Review review) {
        reviews.add(review);
        review.setAusschreibung(this);
    }

    public void removeReview(Review review) {
        reviews.remove(review);
        review.setAusschreibung(null);
    }

     public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
    this.owner = owner;
    }

    @Override
    public String toString() {

        return "Ausschreibung{"
                + "id=" + id
                + ", petName='" + petName + '\''
                + ", petAge=" + petAge
                + ", city='" + city + '\''
                + ", postalCode='" + postalCode + '\''
                + ", animalType='" + animalType + '\''
                + ", description='" + description + '\''
                + ", dateFrom='" + dateFrom + '\''
                + ", dateTo='" + dateTo + '\''
                + ", compensation='" + compensation + '\''
                + ", imageUrl='" + imageUrl + '\''
                + '}';

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ausschreibung that = (Ausschreibung) o;
        return id!= null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
