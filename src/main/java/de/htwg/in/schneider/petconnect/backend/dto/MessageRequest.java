package de.htwg.in.schneider.petconnect.backend.dto;

public class MessageRequest {
private Long receiverId;
private String text;
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
