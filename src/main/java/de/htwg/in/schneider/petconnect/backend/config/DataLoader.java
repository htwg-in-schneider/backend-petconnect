package de.htwg.in.schneider.petconnect.backend.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.htwg.in.schneider.petconnect.backend.model.Ausschreibung;
import de.htwg.in.schneider.petconnect.backend.model.AnimalType;
import de.htwg.in.schneider.petconnect.backend.repository.AusschreibungRepository;
import de.htwg.in.schneider.petconnect.backend.repository.ReviewRepository;
import de.htwg.in.schneider.petconnect.backend.model.Review;
import de.htwg.in.schneider.petconnect.backend.model.User;
import de.htwg.in.schneider.petconnect.backend.model.Role;
import de.htwg.in.schneider.petconnect.backend.repository.UserRepository;
import java.time.LocalDate;

import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;

@Configuration
public class DataLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataLoader.class);

    @Bean
    public CommandLineRunner loadData(UserRepository userRepository, AusschreibungRepository repository, ReviewRepository reviewRepository) {
        return args -> {
            loadInitialUsers(userRepository);

        if (repository.count() == 0) {
            LOGGER.info("Database is empty. Loading initial data...");
            loadInitialData(userRepository, repository, reviewRepository);
            } else {
                LOGGER.info("Database already contains data. Skipping data loading.");
            }
        };
    }

    private void loadInitialUsers(UserRepository userRepository) {

    upsertUser(
            userRepository,
            "Jana",
            "Admin",
            "12345 Adminstadt",
            "alicemuster+admin@petconnect.de",
            "auth0|6a2176926d4bd56b9f690a2d",
            Role.ADMIN);

    upsertUser(
            userRepository,
            "Alice",
            "Muster",
            "12345 Musterstadt",
            "alicemuster+besitzer@petconnect.de",
            "auth0|6a217774f78e54ce72cc2d50",
            Role.TIERBESITZER);

    upsertUser(
            userRepository,
            "Alice",
            "Klein",
            "12345 Kleinstadt",
            "alicemuster+sucher@petconnect.de",
            "auth0|6a2177b1009fd25f88a7e9bc",
            Role.TIERSUCHER);
    
    upsertUser(
            userRepository,
            "Bob",
            "2",
            "12345 Adminstadt",
            "alicemuster+besitzer2@gmail.com",
            "auth0|6a2851e1b19c802d64b694b7",
            Role.TIERBESITZER);
}

private void upsertUser(
        UserRepository userRepository,
        String firstName,
        String lastName,
        String address,
        String email,
        String oauthId,
        Role role) {

    Optional<User> existing = userRepository.findByEmail(email);

    if (existing.isPresent()) {

        User user = existing.get();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAddress(address);
        user.setEmail(email);
        user.setOauthId(oauthId);
        user.setRole(role);

        userRepository.save(user);

        LOGGER.info("Updated user {}", email);

    } else {

        User user = new User();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAddress(address);
        user.setEmail(email);
        user.setOauthId(oauthId);
        user.setRole(role);

        userRepository.save(user);

        LOGGER.info("Created user {}", email);
    }
}
    private void loadInitialData(UserRepository userRepository, AusschreibungRepository repository, ReviewRepository reviewRepository) {
        User tierbesitzer = userRepository
        .findByEmail("alicemuster+besitzer@petconnect.de")
        .orElseThrow();

        User tierbesitzer2 = userRepository
        .findByEmail("alicemuster+besitzer2@gmail.com")
        .orElseThrow();
        Ausschreibung a1 = new Ausschreibung();

        a1.setOwner(tierbesitzer);
        a1.setPetName("Bello");
        a1.setPetAge(5);
        a1.setCity("Konstanz");
        a1.setPostalCode("78462");
        a1.setAnimalType(AnimalType.DOG);
        a1.setDescription("Looking for someone to take care of Bello during the weekend.");
        a1.setDateFrom(LocalDate.of(2026, 8, 1));
        a1.setDateTo(LocalDate.of(2026, 8, 4));
        a1.setCompensation("Tausch");
        a1.setImageUrl( "https://images.unsplash.com/photo-1552053831-71594a27632d");

        Ausschreibung a2 = new Ausschreibung();
        a2.setOwner(tierbesitzer);
        a2.setPetName("Milo");
        a2.setPetAge(3);
        a2.setCity("Singen");
        a2.setPostalCode("78224");
        a2.setAnimalType(AnimalType.CAT);
        a2.setDescription("Need a cat sitter during vacation." );
        a2.setDateFrom(LocalDate.of(2026, 7, 10));
        a2.setDateTo(LocalDate.of(2026, 7, 20));
        a2.setCompensation("Bezahlung");
        a2.setImageUrl("https://images.unsplash.com/photo-1519052537078-e6302a4968d4");

        Ausschreibung a3 = new Ausschreibung();
        a3.setOwner(tierbesitzer);
        a3.setPetName("Luna");
        a3.setPetAge(2);
        a3.setCity("Radolfzell");
        a3.setPostalCode("78315");
        a3.setAnimalType(AnimalType.RABBIT);
        a3.setDescription("Looking for a loving rabbit sitter.");
        a3.setDateFrom(LocalDate.of(2026, 9, 5));
        a3.setDateTo(LocalDate.of(2026, 9, 12));
        a3.setCompensation("Bezahlung");
        a3.setImageUrl("https://images.unsplash.com/photo-1585110396000-c9ffd4e4b308");
        repository.saveAll(Arrays.asList(a1, a2, a3));

        Ausschreibung a4 = new Ausschreibung();
        a4.setOwner(tierbesitzer2);
        a4.setPetName("Fluffy");
        a4.setPetAge(1);
        a4.setCity("Löffingen");
        a4.setPostalCode("79843");
        a4.setAnimalType(AnimalType.BIRD);
        a4.setDescription("Need a bird sitter for my parrot Fluffy during the week.");
        a4.setDateFrom(LocalDate.of(2026, 8, 5));
        a4.setDateTo(LocalDate.of(2026, 8, 12));
        a4.setCompensation("Bezahlung");
        a4.setImageUrl("https://images.unsplash.com/photo-1552728089-57bdde30beb3");
        repository.saveAll(Arrays.asList(a1, a2, a3, a4));
        

        //Add reviews
        Review r1 = new Review();
        r1.setAusschreibung(a1);
        r1.setStars(5);
        r1.setText("Sehr liebevoller Umgang mit Bello, gerne wieder!");
        r1.setUserName("Anna");

        Review r2 = new Review();
        r2.setAusschreibung(a1);
        r2.setStars(4);
        r2.setText("Bello war glücklich, aber es gab ein kleines Missverständnis bei der Fütterung.");
        r2.setUserName("Max");

        Review r3 = new Review();
        r3.setAusschreibung(a2);
        r3.setStars(5);
        r3.setText("Milo wurde bestens versorgt, sehr empfehlenswert!");
        r3.setUserName("Sophie");
        
        reviewRepository.saveAll(Arrays.asList(r1, r2, r3));

        LOGGER.info("Initial data loaded successfully.");

    }
}
