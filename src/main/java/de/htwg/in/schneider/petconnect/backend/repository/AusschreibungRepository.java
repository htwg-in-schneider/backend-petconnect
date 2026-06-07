package de.htwg.in.schneider.petconnect.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.htwg.in.schneider.petconnect.backend.model.Ausschreibung;
import de.htwg.in.schneider.petconnect.backend.model.User;

@Repository
public interface AusschreibungRepository extends JpaRepository<Ausschreibung, Long> {
    List<Ausschreibung> findByOwner(User owner);
}
