package employees;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class ApplicationUris {

    public URI getUriForEmployee(UriComponentsBuilder uri, long employeeId) {
        return uri.path("/api/employees/{id}").buildAndExpand(employeeId).toUri();
    }
}
