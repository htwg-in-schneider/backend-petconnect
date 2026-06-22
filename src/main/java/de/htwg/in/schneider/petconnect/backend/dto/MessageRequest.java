package de.htwg.in.schneider.petconnect.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class MessageRequest {
    @NotNull
    private Long receiverId;
    @NotBlank
    @Size(max = 2000, message = "Nachricht darf maximal 2000 Zeichen lang sein")
    private String text;
    @NotNull
    private Long ausschreibungId;

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }  
    public Long getAusschreibungId() {
        return ausschreibungId;
    }
    public void setAusschreibungId(Long ausschreibungId) {
        this.ausschreibungId = ausschreibungId;
    }
}
