package ma.mlilab.gateway;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ProxyController {

    private final RestTemplate restTemplate;
    private static final String CLIENT_SERVICE = "http://SERVICE-CLIENT";

    public ProxyController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/clients")
    public ResponseEntity<String> proxyClients() {
        return restTemplate.getForEntity(CLIENT_SERVICE + "/clients", String.class);
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<String> proxyClient(@PathVariable String id) {
        return restTemplate.getForEntity(CLIENT_SERVICE + "/client/" + id, String.class);
    }

    // Alias paths when on appelle via /SERVICE-CLIENT/**
    @GetMapping("/SERVICE-CLIENT/clients")
    public ResponseEntity<String> proxyClientsAlias() {
        return proxyClients();
    }

    @GetMapping("/SERVICE-CLIENT/client/{id}")
    public ResponseEntity<String> proxyClientAlias(@PathVariable String id) {
        return proxyClient(id);
    }
}
