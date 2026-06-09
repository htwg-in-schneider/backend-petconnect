package de.htwg.in.schneider.petconnect.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

import de.htwg.in.schneider.petconnect.backend.model.Role;
import de.htwg.in.schneider.petconnect.backend.model.User;
import de.htwg.in.schneider.petconnect.backend.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {

@Autowired
private UserRepository userRepository;

private boolean userFromJwtIsAdmin(Jwt jwt) {
    if (jwt == null || jwt.getSubject() == null) {
        return false;
    }
    Optional<User> user =
            userRepository.findByOauthId(
                    jwt.getSubject());
    return user.isPresent()
            && user.get().getRole() == Role.ADMIN;
}

// ADMIN: Alle Benutzer sehen
@GetMapping
public ResponseEntity<List<User>> getUsers(
        @AuthenticationPrincipal Jwt jwt) {

    if (!userFromJwtIsAdmin(jwt)) {
        return ResponseEntity.status(403).build();
    }

    return ResponseEntity.ok(
            userRepository.findAll());
}

// ADMIN: Benutzer bearbeiten
@PutMapping("/{id}")
public ResponseEntity<User> updateUser(
        @PathVariable Long id,
        @RequestBody User updatedUser,
        @AuthenticationPrincipal Jwt jwt) {

    if (!userFromJwtIsAdmin(jwt)) {
        return ResponseEntity.status(403).build();
    }
    Optional<User> opt =
            userRepository.findById(id);

    if (opt.isEmpty()) {
        return ResponseEntity.notFound().build();
    }

    User user = opt.get();

    user.setFirstName(updatedUser.getFirstName());
    user.setLastName(updatedUser.getLastName());
    user.setRole(updatedUser.getRole());

    userRepository.save(user);

    return ResponseEntity.ok(user);
}

// USER: Eigenes Profil ändern
  @PutMapping("/me")
    public ResponseEntity<User> updateOwnProfile(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody User updatedUser) {

        Optional<User> opt =
                userRepository.findByOauthId(
                        jwt.getSubject());

        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = opt.get();

        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        // KEIN user.setRole(...)

        userRepository.save(user);

        return ResponseEntity.ok(user);
    }
}