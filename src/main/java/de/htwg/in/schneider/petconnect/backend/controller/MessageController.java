package de.htwg.in.schneider.petconnect.backend.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

import de.htwg.in.schneider.petconnect.backend.dto.MessageRequest;
import de.htwg.in.schneider.petconnect.backend.repository.MessageRepository;
import de.htwg.in.schneider.petconnect.backend.repository.UserRepository;
import de.htwg.in.schneider.petconnect.backend.model.Message;
import de.htwg.in.schneider.petconnect.backend.model.User;
import de.htwg.in.schneider.petconnect.backend.dto.ChatOverview;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

@Autowired
private MessageRepository messageRepository;

@Autowired
private UserRepository userRepository;

@PostMapping
public ResponseEntity<?> sendMessage(@AuthenticationPrincipal Jwt jwt,@RequestBody MessageRequest request) {

User sender = userRepository.findByOauthId(jwt.getSubject())
                    .orElseThrow();

User receiver = userRepository.findById(request.getReceiverId())
                    .orElseThrow();

Message message = new Message();

message.setSender(sender);
message.setReceiver(receiver);
message.setText(request.getText());
message.setSentAt(LocalDateTime.now());

messageRepository.save(message);

return ResponseEntity.ok().build();
}

@GetMapping("/chat/{userId}")
public List<Message> getChat(@AuthenticationPrincipal Jwt jwt,@PathVariable Long userId) {

    User currentUser =userRepository.findByOauthId(jwt.getSubject())
                    .orElseThrow();

    return messageRepository.findBySenderIdAndReceiverIdOrSenderIdAndReceiverId(
                    currentUser.getId(),
                    userId,
                    userId,
                    currentUser.getId()
            );
}

@GetMapping
public List<Message> getMyMessages(
        @AuthenticationPrincipal Jwt jwt) {

    User currentUser = userRepository.findByOauthId(jwt.getSubject()) 
                        .orElseThrow();

    return messageRepository.findBySenderIdOrReceiverId(
            currentUser.getId(),
            currentUser.getId()
        );
}

@GetMapping("/overview")
public List<ChatOverview> getChatOverview(
        @AuthenticationPrincipal Jwt jwt) {

    User currentUser =
        userRepository.findByOauthId(
            jwt.getSubject()
        ).orElseThrow();

    List<Message> messages =
        messageRepository
            .findBySenderIdOrReceiverId(
                currentUser.getId(),
                currentUser.getId());

    Map<Long, ChatOverview> chats =
        new HashMap<>();

    for (Message message : messages) {

        User otherUser;

        if (message.getSender().getId()
                .equals(currentUser.getId())) {

            otherUser =
                message.getReceiver();

        } else {

            otherUser =
                message.getSender();
        }

        chats.put(
            otherUser.getId(),
            new ChatOverview(
                otherUser.getId(),
                otherUser.getFirstName(),
                message.getText()
            )
        );
    }

    return new ArrayList<>(chats.values());
}

}
