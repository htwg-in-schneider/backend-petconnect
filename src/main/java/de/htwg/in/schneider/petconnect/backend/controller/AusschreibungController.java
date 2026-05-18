package de.htwg.in.schneider.petconnect.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import de.htwg.in.schneider.petconnect.backend.model.Ausschreibung;
import de.htwg.in.schneider.petconnect.backend.repository.AusschreibungRepository;

import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/ausschreibungen")
public class AusschreibungController {

    private static final Logger LOG = LoggerFactory.getLogger(AusschreibungController.class);

    @Autowired
    private AusschreibungRepository ausschreibungRepository;


    // GET ALL
    @GetMapping
    public List<Ausschreibung> getAusschreibungen() {
        return ausschreibungRepository.findAll();
    }

    
    //CREATE
    @PostMapping
    public Ausschreibung createAusschreibung(@RequestBody Ausschreibung ausschreibung) {
        if (ausschreibung.getId() != null) {
            ausschreibung.setId(null);
            LOG.warn("Attempted to create an ausschreibung with an existing ID. ID has been set to null to create a new ausschreibung.");
        }
        Ausschreibung newAusschreibung = ausschreibungRepository.save(ausschreibung);
        LOG.info("Created new ausschreibung with id " + newAusschreibung.getId());
        return newAusschreibung;
    }

    //UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Ausschreibung> updateAusschreibung(@PathVariable Long id, @RequestBody Ausschreibung ausschreibungDetails) {
        Optional<Ausschreibung> opt = ausschreibungRepository.findById(id);
        if (!opt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Ausschreibung ausschreibung = opt.get();
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
    public ResponseEntity<Object> deleteAusschreibung(@PathVariable Long id) {
        Optional<Ausschreibung> opt = ausschreibungRepository.findById(id);
        if (!opt.isPresent()) {
            return ResponseEntity.notFound().build();
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
}