package ma.enset.client.controllers;

import ma.enset.client.entities.Client;
import ma.enset.client.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClientController {
	@Autowired
	private ClientService clientService;

	@GetMapping("/clients")
	public List<Client> findAll() {
		return clientService.getAllClients();
	}

	@GetMapping("/client/{id}")
	public Client findById(@PathVariable Long id) {
		return clientService.getClient(id);
	}
}
