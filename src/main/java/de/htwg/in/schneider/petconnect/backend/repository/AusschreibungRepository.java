package de.htwg.in.schneider.petconnect.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.htwg.in.schneider.petconnect.backend.model.Ausschreibung;

@Repository
public interface AusschreibungRepository extends JpaRepository<Ausschreibung, Long> {
}
