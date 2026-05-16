package de.htwg.in.schneider.petconnect.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class Ausschreibung {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String petName;
    private int petAge;
    private String city;
    private String postalCode;
    @Enumerated(EnumType.STRING)
    private AnimalType animalType;
    private String description;
    private String dateFrom;
    private String dateTo;
    private String compensation;
    private String imageUrl;

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

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
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
