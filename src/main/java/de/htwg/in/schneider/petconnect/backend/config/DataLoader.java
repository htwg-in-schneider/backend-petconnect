package de.htwg.in.schneider.petconnect.backend.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.htwg.in.schneider.petconnect.backend.model.Ausschreibung;
import de.htwg.in.schneider.petconnect.backend.model.Meldung;
import de.htwg.in.schneider.petconnect.backend.model.AnimalType;
import de.htwg.in.schneider.petconnect.backend.model.AnfrageStatus;
import de.htwg.in.schneider.petconnect.backend.model.Betreuungsanfrage;
import de.htwg.in.schneider.petconnect.backend.repository.AusschreibungRepository;
import de.htwg.in.schneider.petconnect.backend.repository.BetreuungsanfrageRepository;
import de.htwg.in.schneider.petconnect.backend.repository.MeldungRepository;
import de.htwg.in.schneider.petconnect.backend.repository.ReviewRepository;
import de.htwg.in.schneider.petconnect.backend.model.Review;
import de.htwg.in.schneider.petconnect.backend.model.User;
import de.htwg.in.schneider.petconnect.backend.model.Role;
import de.htwg.in.schneider.petconnect.backend.repository.UserRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;

@Configuration
public class DataLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataLoader.class);

    @Bean
    public CommandLineRunner loadData(
            UserRepository userRepository,
            AusschreibungRepository repository,
            ReviewRepository reviewRepository,
            BetreuungsanfrageRepository anfrageRepository,
            MeldungRepository meldungRepository) {
        return args -> {
            loadInitialUsers(userRepository);
            if (repository.count() == 0) {
                LOGGER.info("Database is empty. Loading initial data...");
                loadInitialData(userRepository, repository, reviewRepository, anfrageRepository,meldungRepository);
            } else {
                LOGGER.info("Database already contains data. Skipping data loading.");
            }
        };
    }

    private void loadInitialUsers(UserRepository userRepository) {
        upsertUser(userRepository, "Jana", "Admin", "12345 Adminstadt",
                "alicemuster+admin@petconnect.de", "auth0|6a2176926d4bd56b9f690a2d", Role.ADMIN);
        upsertUser(userRepository, "Alice", "Muster", "Seestraße 12, 78462 Konstanz",
                "alicemuster+besitzer@petconnect.de", "auth0|6a217774f78e54ce72cc2d50", Role.TIERBESITZER);
        upsertUser(userRepository, "Simon", "Klein", "Hauptstraße 5, 78224 Singen",
                "alicemuster+sucher@petconnect.de", "auth0|6a2177b1009fd25f88a7e9bc", Role.TIERSUCHER);
        upsertUser(userRepository, "Bob", "Müller", "Gartenweg 3, 78315 Radolfzell",
                "alicemuster+besitzer2@gmail.com", "auth0|6a2851e1b19c802d64b694b7", Role.TIERBESITZER);
    }

    private void upsertUser(UserRepository userRepository, String firstName, String lastName,
            String address, String email, String oauthId, Role role) {
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

    private void loadInitialData(
            UserRepository userRepository,
            AusschreibungRepository repository,
            ReviewRepository reviewRepository,
            BetreuungsanfrageRepository anfrageRepository,
            MeldungRepository meldungRepository) {

        User tierbesitzer = userRepository
                .findByEmail("alicemuster+besitzer@petconnect.de")
                .orElseThrow();
        User tierbesitzer2 = userRepository
                .findByEmail("alicemuster+besitzer2@gmail.com")
                .orElseThrow();
        User tiersucher = userRepository
                .findByEmail("alicemuster+sucher@petconnect.de")
                .orElseThrow();

        // ── VERFÜGBARE Ausschreibungen ──────────────────────────────

        Ausschreibung a1 = new Ausschreibung();
        a1.setOwner(tierbesitzer);
        a1.setPetName("Bello");
        a1.setPetAge(5);
        a1.setCity("Konstanz");
        a1.setPostalCode("78462");
        a1.setAnimalType(AnimalType.DOG);
        a1.setDescription("Bello ist ein freundlicher Golden Retriever und braucht zweimal täglich einen langen Spaziergang. Er versteht sich gut mit anderen Hunden und Kindern.");
        a1.setDateFrom(LocalDate.of(2026, 8, 1));
        a1.setDateTo(LocalDate.of(2026, 8, 4));
        a1.setCompensation("Tausch");
        a1.setImageUrl("https://images.unsplash.com/photo-1625556580790-7ce9101965b1?q=80&w=1074&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");

        Ausschreibung a2 = new Ausschreibung();
        a2.setOwner(tierbesitzer);
        a2.setPetName("Milo");
        a2.setPetAge(3);
        a2.setCity("Singen");
        a2.setPostalCode("78224");
        a2.setAnimalType(AnimalType.CAT);
        a2.setDescription("Milo ist eine ruhige Hauskatze die zweimal täglich gefüttert werden muss. Er ist sehr verschmust und liebt es gestreichelt zu werden.");
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
        a3.setDescription("Luna ist ein sanftes Kaninchen das viel Auslauf braucht. Sie frisst Heu, frisches Gemüse und Pellets. Sehr zutraulich und pflegeleicht.");
        a3.setDateFrom(LocalDate.of(2026, 9, 5));
        a3.setDateTo(LocalDate.of(2026, 9, 12));
        a3.setCompensation("Bezahlung");
        a3.setImageUrl("https://images.unsplash.com/photo-1585110396000-c9ffd4e4b308");

        Ausschreibung a4 = new Ausschreibung();
        a4.setOwner(tierbesitzer2);
        a4.setPetName("Fluffy");
        a4.setPetAge(1);
        a4.setCity("Löffingen");
        a4.setPostalCode("79843");
        a4.setAnimalType(AnimalType.BIRD);
        a4.setDescription("Fluffy ist ein bunter Papagei der gerne redet und Gesellschaft braucht. Er muss täglich frisches Obst und Wasser bekommen und braucht mindestens eine Stunde Freiflug.");
        a4.setDateFrom(LocalDate.of(2026, 8, 5));
        a4.setDateTo(LocalDate.of(2026, 8, 12));
        a4.setCompensation("Bezahlung");
        a4.setImageUrl("https://images.unsplash.com/photo-1648222473707-2c35edc61c27?q=80&w=1078&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");

        Ausschreibung a5 = new Ausschreibung();
        a5.setOwner(tierbesitzer2);
        a5.setPetName("Rocky");
        a5.setPetAge(4);
        a5.setCity("Stockach");
        a5.setPostalCode("78333");
        a5.setAnimalType(AnimalType.DOG);
        a5.setDescription("Rocky ist ein energiegeladener Labrador der viel Bewegung braucht. Dreimal täglich Gassi, am liebsten im Wald. Verträgt sich nicht mit Katzen.");
        a5.setDateFrom(LocalDate.of(2026, 10, 1));
        a5.setDateTo(LocalDate.of(2026, 10, 7));
        a5.setCompensation("Tausch");
        a5.setImageUrl("https://images.unsplash.com/photo-1587300003388-59208cc962cb");

         Ausschreibung a6 = new Ausschreibung();
        a6.setOwner(tierbesitzer2);
        a6.setPetName("Wurzel");
        a6.setPetAge(2);
        a6.setCity("Stuttgart");
        a6.setPostalCode("70557");
        a6.setAnimalType(AnimalType.CAT);
        a6.setDescription("Verschmuste Wurzel liebt es gekrault zu werden und spielt auch sehr gerne. Sie liegt gerne in der Sonne und genießt die Wärme. Wenn sie zu lang liegt, bitte in den Schatten bringen.");
        a6.setDateFrom(LocalDate.of(2027, 01, 2));
        a6.setDateTo(LocalDate.of(2027,01, 5));
        a6.setCompensation("Bezahlung");
        a6.setImageUrl("https://cdn.pixabay.com/photo/2017/08/07/16/36/cat-2605502_1280.jpg");

        Ausschreibung aMeldung = new Ausschreibung();
        aMeldung.setOwner(tierbesitzer2);
        aMeldung.setPetName("Spike");
        aMeldung.setPetAge(4);
        aMeldung.setCity("Konstanz");
        aMeldung.setPostalCode("78462");
        aMeldung.setAnimalType(AnimalType.DOG);
        aMeldung.setDescription("Brauche dringend jemanden der auf meinen Hund aufpasst. Bin oft nicht zuhause. Spike beißt manchmal aber das ist ok. Bezahlung nur bar, kein Vertrag, keine Fragen. Wer Probleme macht fliegt raus.");
        aMeldung.setDateFrom(LocalDate.of(2026, 7, 5));
        aMeldung.setDateTo(LocalDate.of(2026, 7, 10));
        aMeldung.setCompensation("Bezahlung");
        aMeldung.setImageUrl("https://images.unsplash.com/photo-1581674210461-69414705fa38?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
        repository.saveAll(Arrays.asList(a1, a2, a3, a4, a5,a6, aMeldung));

        Meldung m1 = new Meldung();
m1.setGrund("Unangemessener Inhalt");
m1.setBeschreibung("Das Profilbild ist völlig unangemessen und das Inserat wirkt sehr unseriös. Kein Vertrag, keine Fragen, Hund beißt – das ist gefährlich für andere Nutzer.");
m1.setMeldenderUser(tiersucher);
m1.setGemeldeterUser(tierbesitzer2);
m1.setCreatedAt(LocalDateTime.now());
meldungRepository.save(m1);

        // ── ABGESCHLOSSENE Ausschreibung mit Reviews ────────────────

        Ausschreibung aAbgeschlossen = new Ausschreibung();
        aAbgeschlossen.setOwner(tierbesitzer);
        aAbgeschlossen.setPetName("Max");
        aAbgeschlossen.setPetAge(7);
        aAbgeschlossen.setCity("Konstanz");
        aAbgeschlossen.setPostalCode("78462");
        aAbgeschlossen.setAnimalType(AnimalType.DOG);
        aAbgeschlossen.setDescription("Max ist ein ruhiger alter Hund der nur kurze Spaziergänge braucht. Sehr lieb und kinderfreundlich.");
        aAbgeschlossen.setDateFrom(LocalDate.of(2026, 5, 1));
        aAbgeschlossen.setDateTo(LocalDate.of(2026, 5, 7));
        aAbgeschlossen.setCompensation("Bezahlung");
        aAbgeschlossen.setImageUrl("https://images.unsplash.com/photo-1543466835-00a7907e9de1");
        aAbgeschlossen.setStatus(Ausschreibung.AusschreibungStatus.ABGESCHLOSSEN);
        aAbgeschlossen.setBetreuer(tiersucher);
        repository.save(aAbgeschlossen);

        // Betreuungsanfrage für abgeschlossene Ausschreibung
        Betreuungsanfrage anfrage = new Betreuungsanfrage();
        anfrage.setRequester(tiersucher);
        anfrage.setAusschreibung(aAbgeschlossen);
        anfrage.setStatus(AnfrageStatus.ANGENOMMEN);
        anfrageRepository.save(anfrage);

        // Reviews für abgeschlossene Ausschreibung
        Review r1 = new Review();
        r1.setReviewer(tiersucher);
        r1.setReviewedUser(tierbesitzer);
        r1.setAusschreibung(aAbgeschlossen);
        r1.setStars(5);
        r1.setText("Max ist ein absoluter Traumhund! Die Betreuung hat viel Spaß gemacht, sehr unkomplizierte Übergabe und tolle Kommunikation.");

        Review r2 = new Review();
        r2.setReviewer(tierbesitzer);
        r2.setReviewedUser(tiersucher);
        r2.setAusschreibung(aAbgeschlossen);
        r2.setStars(5);
        r2.setText("Sehr zuverlässige Betreuung, Max wurde bestens versorgt. Jederzeit wieder!");

        reviewRepository.saveAll(Arrays.asList(r1, r2));

        LOGGER.info("Initial data loaded successfully.");
    }
}