package employees.addressesgateway;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "addresses-gateway")
@Data
public class AddressesGatewayProperties {

    private String url;
}
