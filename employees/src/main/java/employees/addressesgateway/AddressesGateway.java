package employees.addressesgateway;

import employees.Gateway;
import org.springframework.web.reactive.function.client.WebClient;

@Gateway
public class AddressesGateway {

    public Address getAddressForEmployee(String name) {
        return WebClient
                .create("http://localhost:8081")
                .get()
                .uri(b -> b.path("/api/addresses").queryParam("name", name).build())
                .exchangeToMono(response -> response.bodyToMono(Address.class))
                .block();
    }
}
