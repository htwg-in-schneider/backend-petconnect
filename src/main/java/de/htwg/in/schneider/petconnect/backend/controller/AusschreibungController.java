package de.htwg.in.schneider.petconnect.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

import de.htwg.in.schneider.petconnect.backend.model.Ausschreibung;
import de.htwg.in.schneider.petconnect.backend.repository.AusschreibungRepository;
import de.htwg.in.schneider.petconnect.backend.model.Role;
import de.htwg.in.schneider.petconnect.backend.model.User;
import de.htwg.in.schneider.petconnect.backend.repository.UserRepository;

import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/ausschreibungen")
public class AusschreibungController {

    private static final Logger LOG = LoggerFactory.getLogger(AusschreibungController.class);

    @Autowired
    private AusschreibungRepository ausschreibungRepository;

    @Autowired
    private UserRepository userRepository;

    private boolean userFromJwtIsTierbesitzer(Jwt jwt) {
    if (jwt == null || jwt.getSubject() == null) {
        return false;
    }
    Optional<User> user = userRepository.findByOauthId(jwt.getSubject());
    return user.isPresent()&& user.get().getRole() == Role.TIERBESITZER;
    }

    // GET ALL
    @GetMapping
    public List<Ausschreibung> getAusschreibungen() {
        return ausschreibungRepository.findAll();
    }

    
    //CREATE
    @PostMapping
    public ResponseEntity<Ausschreibung> createAusschreibung(@AuthenticationPrincipal Jwt jwt,
        @Valid @RequestBody Ausschreibung ausschreibung) {
        if (!userFromJwtIsTierbesitzer(jwt)) {
            return ResponseEntity.status(403).build();
        }
        if (ausschreibung.getId() != null) {
            ausschreibung.setId(null);
            LOG.warn("Attempted to create an ausschreibung with an existing ID. ID has been set to null to create a new ausschreibung.");
        }
        if (ausschreibung.getDateFrom().isAfter(ausschreibung.getDateTo())) {
        return ResponseEntity
            .badRequest()
            .build();
    }
        User owner =userRepository.findByOauthId(jwt.getSubject()).get();
        ausschreibung.setOwner(owner);
        Ausschreibung newAusschreibung = ausschreibungRepository.save(ausschreibung);
        LOG.info("Created new ausschreibung with id " + newAusschreibung.getId());
        return ResponseEntity.ok(newAusschreibung);
    }

    //UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Ausschreibung> updateAusschreibung(@AuthenticationPrincipal Jwt jwt,
        @PathVariable Long id,@Valid @RequestBody Ausschreibung ausschreibungDetails) {
        Optional<Ausschreibung> opt = ausschreibungRepository.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<User> currentUserOpt =userRepository.findByOauthId(jwt.getSubject());
        if (currentUserOpt.isEmpty()) {
        return ResponseEntity.status(404).build();
        }
        User currentUser = currentUserOpt.get();

        Ausschreibung ausschreibung = opt.get();
        if (currentUser.getRole() != Role.ADMIN &&!ausschreibung.getOwner().getId()
            .equals(currentUser.getId())) {
        return ResponseEntity.status(403).build();
        }
         if (ausschreibungDetails.getDateFrom().isAfter(ausschreibungDetails.getDateTo())) {
        return ResponseEntity.badRequest().build();
        }

        ausschreibung.setPetName(ausschreibungDetails.getPetName());
        ausschreibung.setPetAge(ausschreibungDetails.getPetAge());
        ausschreibung.setCity(ausschreibungDetails.getCity());
        ausschreibung.setPostalCode(ausschreibungDetails.getPostalCode());
        ausschreibung.setAnimalType(ausschreibungDetails.getAnimalType());
        ausschreibung.setDescription(ausschreibungDetails.getDescription());
        ausschreibung.setDateFrom(ausschreibungDetails.getDateFrom());
        ausschreibung.setDateTo(ausschreibungDetails.getDateTo());
        ausschreibung.setCompensation(ausschreibungDetails.getCompensation());
        ausschreibung.setImageUrl(ausschreibungDetails.getImageUrl());
        Ausschreibung updatedAusschreibung = ausschreibungRepository.save(ausschreibung);
        LOG.info("Updated ausschreibung with id " + updatedAusschreibung.getId());
        return ResponseEntity.ok(updatedAusschreibung);
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAusschreibung(@AuthenticationPrincipal Jwt jwt,
        @PathVariable Long id) {
            System.out.println("DELETE CONTROLLER ERREICHT");
    System.out.println(jwt.getSubject());

    Optional<Ausschreibung> opt = ausschreibungRepository.findById(id);
    if (opt.isEmpty()) {
        return ResponseEntity.notFound().build();
    }

    User currentUser =userRepository.findByOauthId(jwt.getSubject()).orElseThrow();
    Ausschreibung ausschreibung = opt.get();


System.out.println(currentUser.getRole());

    if (currentUser.getRole() != Role.ADMIN && 
    !ausschreibung.getOwner().getId().equals(currentUser.getId())) {
    return ResponseEntity.status(403).build();
    }
    ausschreibungRepository.delete(opt.get());
    LOG.info("Deleted ausschreibung with id " + id);
    return ResponseEntity.noContent().build();
    }

    //GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Ausschreibung> getAusschreibungById(@PathVariable Long id) {
    Optional<Ausschreibung> opt = ausschreibungRepository.findById(id);
    if (opt.isPresent()) {
    return ResponseEntity.ok(opt.get());
    } else {
    return ResponseEntity.notFound().build();
    }
    }

    @GetMapping("/meine")
    public ResponseEntity<List<Ausschreibung>> getMeineAusschreibungen(@AuthenticationPrincipal Jwt jwt) {
    if (!userFromJwtIsTierbesitzer(jwt)) {
        return ResponseEntity.status(403).build();
    }
            
    User owner =userRepository.findByOauthId(jwt.getSubject()).get();

    return ResponseEntity.ok(ausschreibungRepository.findByOwner(owner));
    }
    @PostMapping("/{id}/complete")
    public ResponseEntity<?> completeBetreuung(
        @AuthenticationPrincipal Jwt jwt,
        @PathVariable Long id) {
        System.out.println("COMPLETE CONTROLLER ERREICHT");
        
    User currentUser = userRepository.findByOauthId(jwt.getSubject()).orElseThrow();
    Ausschreibung ausschreibung = ausschreibungRepository.findById(id).orElseThrow();
    // prüfen, ob User der Owner ist
    if (!ausschreibung.getOwner().getId().equals(currentUser.getId())) {
        return ResponseEntity.status(403).build();
    }
    // prüfen, ob Status VERGEBEN ist
    if (ausschreibung.getStatus() != Ausschreibung.AusschreibungStatus.VERGEBEN) {
        return ResponseEntity.badRequest().build();
    }
    // ERST DANN Status ändern
    ausschreibung.setStatus(Ausschreibung.AusschreibungStatus.ABGESCHLOSSEN);
    ausschreibungRepository.save(ausschreibung);
    
    return ResponseEntity.ok().build();
}}
        