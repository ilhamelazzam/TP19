package ma.mlilab.servicevoiture.web;

import ma.mlilab.servicevoiture.entities.Voiture;
import ma.mlilab.servicevoiture.feign.ClientService;
import ma.mlilab.servicevoiture.model.Client;
import ma.mlilab.servicevoiture.repositories.VoitureRepository;
import ma.mlilab.servicevoiture.services.VoitureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VoitureController {

    private final VoitureRepository voitureRepository;
    private final VoitureService voitureService;
    private final ClientService clientService;

    public VoitureController(VoitureRepository voitureRepository, VoitureService voitureService,
                             ClientService clientService) {
        this.voitureRepository = voitureRepository;
        this.voitureService = voitureService;
        this.clientService = clientService;
    }

    @GetMapping(value = "/voitures", produces = {"application/json"})
    public ResponseEntity<Object> findAll() {
        try {
            List<Voiture> voitures = voitureRepository.findAll();
            voitures.forEach(this::attachClient);
            return ResponseEntity.ok(voitures);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error fetching voitures: " + e.getMessage());
        }
    }

    @GetMapping("/voitures/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        try {
            Voiture voiture = voitureRepository.findById(id)
                .orElseThrow(() -> new Exception("Voiture Introuvable"));

            attachClient(voiture);

            return ResponseEntity.ok(voiture);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Voiture not found with ID: " + id);
        }
    }

    @GetMapping("/voitures/client/{id}")
    public ResponseEntity<List<Voiture>> findByClient(@PathVariable Long id) {
        try {
            Client client = clientService.clientById(id);
            if (client != null) {
                List<Voiture> voitures = voitureRepository.findByClientId(id);
                voitures.forEach(v -> v.setClient(client));
                return ResponseEntity.ok(voitures);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/voitures/{clientId}")
    public ResponseEntity<Object> save(@PathVariable Long clientId, @RequestBody Voiture voiture) {
        try {
            Client client = clientService.clientById(clientId);

            if (client != null) {
                voiture.setClient(client);
                voiture.setClientId(clientId);
                Voiture savedVoiture = voitureService.enregistrerVoiture(voiture);
                return ResponseEntity.ok(savedVoiture);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Client not found with ID: " + clientId);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error saving voiture: " + e.getMessage());
        }
    }

    @PutMapping("/voitures/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody Voiture updatedVoiture) {
        try {
            Voiture existingVoiture = voitureRepository.findById(id)
                .orElseThrow(() -> new Exception("Voiture not found with ID: " + id));

            if (updatedVoiture.getMatricule() != null && !updatedVoiture.getMatricule().isEmpty()) {
                existingVoiture.setMatricule(updatedVoiture.getMatricule());
            }
            if (updatedVoiture.getMarque() != null && !updatedVoiture.getMarque().isEmpty()) {
                existingVoiture.setMarque(updatedVoiture.getMarque());
            }
            if (updatedVoiture.getModel() != null && !updatedVoiture.getModel().isEmpty()) {
                existingVoiture.setModel(updatedVoiture.getModel());
            }

            Voiture savedVoiture = voitureRepository.save(existingVoiture);
            attachClient(savedVoiture);
            return ResponseEntity.ok(savedVoiture);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error updating voiture: " + e.getMessage());
        }
    }

    private void attachClient(Voiture v) {
        if (v.getClientId() != null) {
            Client c = clientService.clientById(v.getClientId());
            v.setClient(c);
        }
    }
}
