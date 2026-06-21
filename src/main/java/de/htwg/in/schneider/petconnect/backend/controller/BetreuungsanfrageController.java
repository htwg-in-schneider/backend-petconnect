package de.htwg.in.schneider.petconnect.backend.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.htwg.in.schneider.petconnect.backend.dto.BetreuungsanfrageRequest;
import de.htwg.in.schneider.petconnect.backend.model.AnfrageStatus;
import de.htwg.in.schneider.petconnect.backend.model.Ausschreibung;
import de.htwg.in.schneider.petconnect.backend.model.Betreuungsanfrage;
import de.htwg.in.schneider.petconnect.backend.model.Message;
import de.htwg.in.schneider.petconnect.backend.model.User;
import de.htwg.in.schneider.petconnect.backend.repository.AusschreibungRepository;
import de.htwg.in.schneider.petconnect.backend.repository.BetreuungsanfrageRepository;
import de.htwg.in.schneider.petconnect.backend.repository.MessageRepository;
import de.htwg.in.schneider.petconnect.backend.repository.UserRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/anfragen")
public class BetreuungsanfrageController {

@Autowired
private BetreuungsanfrageRepository anfrageRepository;

@Autowired
private AusschreibungRepository ausschreibungRepository;

@Autowired
private UserRepository userRepository;

@Autowired
private MessageRepository messageRepository;

@PostMapping
public ResponseEntity<?> createRequest(@AuthenticationPrincipal Jwt jwt, @Valid
        @RequestBody BetreuungsanfrageRequest dto) {

    User requester =userRepository.findByOauthId(jwt.getSubject())
                .orElseThrow();

    Ausschreibung ausschreibung =ausschreibungRepository.findById(
                dto.getAusschreibungId())
                .orElseThrow();

    Betreuungsanfrage anfrage =new Betreuungsanfrage();

    anfrage.setRequester(requester);
    anfrage.setAusschreibung(ausschreibung);
    anfrage.setStatus(AnfrageStatus.OFFEN);
    anfrageRepository.save(anfrage);

    Message message = new Message();
    message.setSender(requester);
    message.setReceiver(ausschreibung.getOwner());
    message.setText( "Betreuungsanfrage");
    message.setType(Message.MessageType.REQUEST);
    message.setAusschreibung(ausschreibung);
    message.setAnfrage(anfrage);
    message.setSentAt(LocalDateTime.now());
    messageRepository.save(message);

    return ResponseEntity.ok().build();
}

@PostMapping("/{id}/accept")
public ResponseEntity<?> acceptRequest(@PathVariable Long id) {
    Betreuungsanfrage anfrage =anfrageRepository.findById(id)
                                .orElseThrow();
    anfrage.setStatus(AnfrageStatus.ANGENOMMEN);
    Ausschreibung ausschreibung =anfrage.getAusschreibung();
    ausschreibung.setStatus(Ausschreibung.AusschreibungStatus.VERGEBEN);
    ausschreibungRepository.save(ausschreibung);
    anfrageRepository.save(anfrage);

    return ResponseEntity.ok().build();
}

@PostMapping("/{id}/reject")
public ResponseEntity<?> rejectRequest( @PathVariable Long id) {
    Betreuungsanfrage anfrage =anfrageRepository.findById(id)
                        .orElseThrow();
    anfrage.setStatus(AnfrageStatus.ABGELEHNT);
    anfrageRepository.save(anfrage);

    return ResponseEntity.ok().build();
}
}
