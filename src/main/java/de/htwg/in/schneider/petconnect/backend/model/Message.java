package de.htwg.in.schneider.petconnect.backend.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Message {

    public enum MessageType {
        TEXT,
        REQUEST
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(nullable = false)
    private User sender;

    @ManyToOne
    @NotNull
    @JoinColumn(nullable = false)
    private User receiver;

    @NotBlank
    @Column(nullable = false)
    private String text;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime sentAt;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable = false)
    private MessageType type;

    @ManyToOne
    @NotNull
    @JoinColumn(nullable = false)
    private Ausschreibung ausschreibung;

    @ManyToOne
    private Betreuungsanfrage anfrage;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public User getSender() {
        return sender;
    }
    public void setSender(User sender) {
        this.sender = sender;
    }
    public User getReceiver() {
        return receiver;
    }
    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public LocalDateTime getSentAt() {
        return sentAt;
    }
    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }
    public MessageType getType() {
        return type;
    }
    public void setType(MessageType type) {
        this.type = type;
    }
    public Ausschreibung getAusschreibung() {
        return ausschreibung;
    }
    public void setAusschreibung(Ausschreibung ausschreibung) {
        this.ausschreibung = ausschreibung;
    }
    public Betreuungsanfrage getAnfrage() {
        return anfrage;
    }
    public void setAnfrage(Betreuungsanfrage anfrage) {
        this.anfrage = anfrage;
    }
}