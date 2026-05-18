package de.htwg.in.schneider.petconnect.backend.controller;

import org.springframework.web.bind.annotation.*;

import de.htwg.in.schneider.petconnect.backend.model.AnimalType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/animaltype")
@CrossOrigin(origins = "*")
public class AnimalTypeController {

    @GetMapping()
    public List<AnimalType> getAnimalTypes() {
        return Arrays.asList(AnimalType.values());
    }

    @GetMapping("/translation")
    public Map<String, String> getAllAnimalTypes() {
        return Arrays.stream(AnimalType.values())
            .collect(Collectors.toMap(AnimalType::name,AnimalType::getDisplayName));
    }
}
