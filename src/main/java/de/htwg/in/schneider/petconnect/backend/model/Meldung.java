package de.htwg.in.schneider.petconnect.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
public class Meldung {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String grund;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 10, max = 500)
    private String beschreibung;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User gemeldeterUser;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User meldenderUser;

    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getGrund() {
        return grund;
    }
    public void setGrund(String grund) {
        this.grund = grund;
    }
    public String getBeschreibung() {
        return beschreibung;
    }
    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public User getGemeldeterUser() {
        return gemeldeterUser;
    }
    public void setGemeldeterUser(User gemeldeterUser) {
        this.gemeldeterUser = gemeldeterUser;
    }
    public User getMeldenderUser() {
        return meldenderUser;
    }
    public void setMeldenderUser(User meldenderUser) {
        this.meldenderUser = meldenderUser;
    }
}
