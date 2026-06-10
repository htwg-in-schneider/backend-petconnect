package de.htwg.in.schneider.petconnect.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.htwg.in.schneider.petconnect.backend.model.Meldung;
    
@Repository
public interface MeldungRepository extends JpaRepository<Meldung, Long> {
}

