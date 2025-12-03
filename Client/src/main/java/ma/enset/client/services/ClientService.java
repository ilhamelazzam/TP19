package ma.enset.client.services;

import ma.enset.client.entities.Client;

import java.util.List;

public interface ClientService {
	List<Client> getAllClients();

	Client getClient(Long id);
}
