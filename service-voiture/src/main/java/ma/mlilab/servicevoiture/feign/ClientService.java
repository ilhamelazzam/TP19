package ma.mlilab.servicevoiture.feign;

import ma.mlilab.servicevoiture.model.Client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "SERVICE-CLIENT")
public interface ClientService {
    @GetMapping(path = "/client/{id}")
    Client clientById(@PathVariable("id") Long id);
}
