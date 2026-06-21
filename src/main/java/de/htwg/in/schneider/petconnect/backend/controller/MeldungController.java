package de.htwg.in.schneider.petconnect.backend.controller;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import de.htwg.in.schneider.petconnect.backend.model.Meldung;
import de.htwg.in.schneider.petconnect.backend.model.Role;
import de.htwg.in.schneider.petconnect.backend.repository.MeldungRepository;
import de.htwg.in.schneider.petconnect.backend.repository.UserRepository;
import jakarta.validation.Valid;
import de.htwg.in.schneider.petconnect.backend.model.User;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

@RestController
@RequestMapping("/api/meldungen")
public class MeldungController {

@Autowired
private MeldungRepository meldungRepository;

@Autowired
private UserRepository userRepository;

@CrossOrigin(origins = "http://localhost:5173")
@GetMapping
public ResponseEntity<List<Meldung>> getMeldungen(
        @AuthenticationPrincipal Jwt jwt) {
    User currentUser = userRepository
            .findByOauthId(jwt.getSubject())
            .orElseThrow();
    if (currentUser.getRole() != Role.ADMIN) {
        return ResponseEntity.status(403).build();
    }
    return ResponseEntity.ok(meldungRepository.findAll());
}

@PostMapping("/{userId}")
public ResponseEntity<Meldung> createMeldung(
        @AuthenticationPrincipal Jwt jwt,
        @PathVariable Long userId,
        @Valid
        @RequestBody Meldung meldung) {

if (jwt == null || jwt.getSubject() == null) {
    return ResponseEntity.status(401).build();
}

User meldender =userRepository.findByOauthId(
                    jwt.getSubject())
                    .orElseThrow();

User gemeldeter =userRepository.findById(userId)
                    .orElseThrow();

if (meldender.getId().equals(gemeldeter.getId())) {
return ResponseEntity.badRequest().build();
}
meldung.setMeldenderUser(meldender);
meldung.setGemeldeterUser(gemeldeter);
meldung.setCreatedAt(LocalDateTime.now());

return ResponseEntity.ok(meldungRepository.save(meldung));
}

}
