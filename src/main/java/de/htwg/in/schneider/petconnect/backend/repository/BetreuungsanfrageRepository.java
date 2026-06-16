package de.htwg.in.schneider.petconnect.backend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import de.htwg.in.schneider.petconnect.backend.model.Betreuungsanfrage;

public interface BetreuungsanfrageRepository
        extends JpaRepository<Betreuungsanfrage, Long> {

    List<Betreuungsanfrage>
    findByAusschreibungId(Long ausschreibungId);
}
