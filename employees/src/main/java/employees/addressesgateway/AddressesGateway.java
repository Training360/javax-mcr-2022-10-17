package employees.addressesgateway;

import employees.Gateway;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.reactive.function.client.WebClient;

@Gateway
@EnableConfigurationProperties(AddressesGatewayProperties.class)
@AllArgsConstructor
public class AddressesGateway {

    AddressesGatewayProperties addressesGatewayProperties;

    public Address getAddressForEmployee(String name) {
        return WebClient
                .create(addressesGatewayProperties.getUrl())
                .get()
                .uri(b -> b.path("/api/addresses").queryParam("name", name).build())
                .exchangeToMono(response -> response.bodyToMono(Address.class))
                .block();
    }
}
