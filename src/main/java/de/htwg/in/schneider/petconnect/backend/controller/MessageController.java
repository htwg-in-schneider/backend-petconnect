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
import de.htwg.in.schneider.petconnect.backend.repository.AusschreibungRepository;
import de.htwg.in.schneider.petconnect.backend.repository.MessageRepository;
import de.htwg.in.schneider.petconnect.backend.repository.UserRepository;
import jakarta.validation.Valid;
import de.htwg.in.schneider.petconnect.backend.model.Ausschreibung;
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

@Autowired
private AusschreibungRepository ausschreibungRepository;

@PostMapping
public ResponseEntity<?> sendMessage(@AuthenticationPrincipal Jwt jwt,
    @Valid @RequestBody MessageRequest request) {

User sender = userRepository.findByOauthId(jwt.getSubject())
                    .orElseThrow();

User receiver = userRepository.findById(request.getReceiverId())
                    .orElseThrow();

if (sender.getId().equals(receiver.getId())) {
    return ResponseEntity.badRequest().build();
}

Message message = new Message();

message.setSender(sender);
message.setReceiver(receiver);
message.setText(request.getText());
message.setSentAt(LocalDateTime.now());
message.setType(Message.MessageType.TEXT);

Ausschreibung ausschreibung = ausschreibungRepository
        .findById(request.getAusschreibungId())
        .orElseThrow();
 
if (!ausschreibung.getOwner().getId().equals(receiver.getId())
        && !ausschreibung.getOwner().getId().equals(sender.getId())) {
    return ResponseEntity.status(403).build();
}

message.setAusschreibung(ausschreibung);

messageRepository.save(message);

return ResponseEntity.ok().build();
}

@GetMapping("/chat/{userId}/{ausschreibungId}")
public List<Message> getChat(@AuthenticationPrincipal Jwt jwt,@PathVariable Long userId, @PathVariable Long ausschreibungId) {

    User currentUser =userRepository.findByOauthId(jwt.getSubject())
                    .orElseThrow();

    return messageRepository.findByAusschreibungIdAndSenderIdAndReceiverIdOrAusschreibungIdAndSenderIdAndReceiverIdOrderBySentAtAsc(
                    ausschreibungId,
                    currentUser.getId(),
                    userId,

                    ausschreibungId,
                    userId,
                    currentUser.getId()
            );
}

@GetMapping
public List<Message> getMyMessages(
        @AuthenticationPrincipal Jwt jwt) {

    User currentUser = userRepository.findByOauthId(jwt.getSubject()) 
                        .orElseThrow();

    return messageRepository.findBySenderIdOrReceiverIdOrderBySentAtAsc(
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
            .findBySenderIdOrReceiverIdOrderBySentAtAsc(
                currentUser.getId(),
                currentUser.getId());

    Map<String, ChatOverview> chats =
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

        String key =
            otherUser.getId()
            + "-"
            + message.getAusschreibung().getId();
            
        chats.put(
            key,
            new ChatOverview(
                otherUser.getId(),
                otherUser.getFirstName(),
                message.getText(),
                message.getAusschreibung().getId(),
                message.getAusschreibung().getPetName()
            )
        );
    }

    return new ArrayList<>(chats.values());
}

}
