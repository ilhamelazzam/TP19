package ma.mlilab.servicevoiture;

import ma.mlilab.servicevoiture.entities.Voiture;
import ma.mlilab.servicevoiture.feign.ClientService;
import ma.mlilab.servicevoiture.model.Client;
import ma.mlilab.servicevoiture.repositories.VoitureRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ServiceVoitureApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceVoitureApplication.class, args);
    }

    @Bean
    CommandLineRunner seedData(VoitureRepository voitureRepository, ClientService clientService) {
        return args -> {
            Client c1 = clientService.clientById(2L);
            Client c2 = clientService.clientById(1L);

            voitureRepository.save(new Voiture(null, "Toyota", "A 25 333", "Corolla", 1L, c2));
            voitureRepository.save(new Voiture(null, "Renault", "B 6 3456", "Megane", 1L, c2));
            voitureRepository.save(new Voiture(null, "Peugeot", "A 55 4444", "301", 2L, c1));
        };
    }
}
