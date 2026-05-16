package de.htwg.in.schneider.petconnect.backend.controller;

import de.htwg.in.schneider.petconnect.backend.model.Ausschreibung;
import de.htwg.in.schneider.petconnect.backend.model.AnimalType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/ausschreibungen")
public class AusschreibungController {

    @GetMapping
   public List<Ausschreibung> getAusschreibungen() {
        Ausschreibung a1 = new Ausschreibung();
        a1.setId(1);
        a1.setPetName("Bello");
        a1.setPetAge(5);
        a1.setCity("Konstanz");
        a1.setPostalCode("78462");
        a1.setAnimalType(AnimalType.DOG);
        a1.setDescription("Suche einen zuverlässigen Hundesitter für meinen Hund Bello während meines Urlaubs.");
        a1.setDateFrom("2026-08-01");
        a1.setDateTo("2026-08-04");
        a1.setCompensation("Tausch");
        a1.setImageUrl("https://images.unsplash.com/photo-1552053831-71594a27632d");



        Ausschreibung a2 = new Ausschreibung();

        a2.setId(2);
        a2.setPetName("Milo");
        a2.setPetAge(3);
        a2.setCity("Singen");
        a2.setPostalCode("78224");
        a2.setAnimalType(AnimalType.CAT);
        a2.setDescription("Suche eine zuverlässige Katzensitter während meines Urlaubs.");
        a2.setDateFrom("2026-07-10");
        a2.setDateTo("2026-07-20");
        a2.setCompensation("Bezahlung");
        a2.setImageUrl("https://images.unsplash.com/photo-1519052537078-e6302a4968d4");



        return Arrays.asList(a1, a2);
    }

    @PostMapping
    public ResponseEntity<String> createAusschreibung(@RequestBody Ausschreibung ausschreibung) {
        System.out.println(ausschreibung);
        return ResponseEntity.ok("POST successful");
    }
}