package ma.mlilab.servicevoiture.services;

import ma.mlilab.servicevoiture.entities.Voiture;
import ma.mlilab.servicevoiture.repositories.VoitureRepository;
import org.springframework.stereotype.Service;

@Service
public class VoitureService {

    private final VoitureRepository voitureRepository;

    public VoitureService(VoitureRepository voitureRepository) {
        this.voitureRepository = voitureRepository;
    }

    public Voiture enregistrerVoiture(Voiture voiture) {
        return voitureRepository.save(voiture);
    }
}
