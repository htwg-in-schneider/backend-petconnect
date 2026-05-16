package de.htwg.in.schneider.petconnect.backend.model;

public class Ausschreibung {
    private long id;
    private String petName;
    private int petAge;
    private String city;
    private String postalCode;
    private AnimalType animalType;
    private String description;
    private String dateFrom;
    private String dateTo;
    private String compensation;
    private String imageUrl;

    // Getters and setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

}
