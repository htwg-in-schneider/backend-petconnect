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

import de.htwg.in.schneider.petconnect.backend.model.User;
import de.htwg.in.schneider.petconnect.backend.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {

@Autowired
private UserRepository userRepository;

@GetMapping
public List<User> getUsers() {
    return userRepository.findAll();
}
@PutMapping("/{id}")
public ResponseEntity<User> updateUser(
        @PathVariable Long id,
        @RequestBody User updatedUser) {

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
}