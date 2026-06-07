package de.htwg.in.schneider.petconnect.backend.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import de.htwg.in.schneider.petconnect.backend.model.User;
import de.htwg.in.schneider.petconnect.backend.repository.UserRepository;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<User> getProfile(
            @AuthenticationPrincipal Jwt jwt) {

        String oauthId = jwt.getSubject();

        return userRepository.findByOauthId(oauthId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping
    public ResponseEntity<User> updateProfile(@AuthenticationPrincipal Jwt jwt,@RequestBody User updatedUser) {
    Optional<User> userOpt =userRepository.findByOauthId(jwt.getSubject());
    if (!userOpt.isPresent()) {
        return ResponseEntity.notFound().build();
    }
    User user = userOpt.get();
    user.setFirstName(updatedUser.getFirstName());
    user.setLastName(updatedUser.getLastName());
    user.setAddress(updatedUser.getAddress());
    userRepository.save(user);
    return ResponseEntity.ok(user);
}
}