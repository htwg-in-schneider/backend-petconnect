package de.htwg.in.schneider.petconnect.backend.dto;

public class ChatOverview {
    private Long userId;
    private String userName;
    private String lastMessage;

    public ChatOverview(
            Long userId,
            String userName,
            String lastMessage) {

        this.userId = userId;
        this.userName = userName;
        this.lastMessage = lastMessage;
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
}
