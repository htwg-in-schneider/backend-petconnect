package de.htwg.in.schneider.petconnect.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;


import de.htwg.in.schneider.petconnect.backend.model.Ausschreibung;
import de.htwg.in.schneider.petconnect.backend.repository.AusschreibungRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/api/ausschreibungen")
public class AusschreibungController {

    @Autowired
    private AusschreibungRepository ausschreibungRepository;

    @GetMapping
   public List<Ausschreibung> getAusschreibungen() {
       return ausschreibungRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<String> createAusschreibung(@RequestBody Ausschreibung ausschreibung) {
        ausschreibungRepository.save(ausschreibung);
        return ResponseEntity.ok("POST successful");
    }
}