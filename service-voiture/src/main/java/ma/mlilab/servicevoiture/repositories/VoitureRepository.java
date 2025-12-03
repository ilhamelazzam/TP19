package ma.mlilab.servicevoiture.repositories;

import ma.mlilab.servicevoiture.entities.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoitureRepository extends JpaRepository<Voiture, Long> {
    List<Voiture> findByClientId(Long clientId);
}
