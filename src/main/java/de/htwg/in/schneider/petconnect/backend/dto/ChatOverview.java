package de.htwg.in.schneider.petconnect.backend.dto;

public class ChatOverview {
    private Long userId;
    private String userName;
    private String lastMessage;
    private Long ausschreibungId;
    private String petName;

    public ChatOverview(
            Long userId,
            String userName,
            String lastMessage,
            Long ausschreibungId,
            String petName) {

        this.userId = userId;
        this.userName = userName;
        this.lastMessage = lastMessage;
        this.ausschreibungId = ausschreibungId;
        this.petName = petName;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public Long getAusschreibungId() {
        return ausschreibungId;
    }

    public String getPetName() {
        return petName;
    }
}
