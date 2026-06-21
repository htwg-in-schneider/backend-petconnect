package de.htwg.in.schneider.petconnect.backend.dto;

import jakarta.validation.constraints.NotNull;

public class BetreuungsanfrageRequest {

    @NotNull
    private Long ausschreibungId;

    public Long getAusschreibungId() {
        return ausschreibungId;
    }

    public void setAusschreibungId(Long ausschreibungId) {
        this.ausschreibungId = ausschreibungId;
    }
}