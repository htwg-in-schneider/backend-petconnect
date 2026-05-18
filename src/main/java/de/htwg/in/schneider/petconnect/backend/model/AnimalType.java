package de.htwg.in.schneider.petconnect.backend.model;

public enum AnimalType {

    DOG("Hund"),
    CAT("Katze"),
    RABBIT("Hase Kaninchen"),
    BIRD("Vogel");

    private final String displayName;

    AnimalType(String displayName) {
        this.displayName = displayName;
    }
    public String getDisplayName() {
        return displayName;
    }

}
