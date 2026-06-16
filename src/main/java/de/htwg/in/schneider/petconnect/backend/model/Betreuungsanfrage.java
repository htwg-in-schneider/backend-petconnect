package de.htwg.in.schneider.petconnect.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Betreuungsanfrage {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Ausschreibung ausschreibung;

    @ManyToOne
    private User requester;

    @Enumerated(EnumType.STRING)
    private AnfrageStatus status;

    public Long getId() {
        return id;
    }

    public Ausschreibung getAusschreibung() {
        return ausschreibung;
    }

    public void setAusschreibung(Ausschreibung ausschreibung) {
        this.ausschreibung = ausschreibung;
    }

    public User getRequester() {
        return requester;
    }

    public void setRequester(User requester) {
        this.requester = requester;
    }

    public AnfrageStatus getStatus() {
        return status;
    }

    public void setStatus(AnfrageStatus status) {
        this.status = status;
    }

}
