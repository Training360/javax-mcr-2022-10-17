package employees;

import employees.addressesgateway.AddressesGateway;
import employees.eventsgateway.EventsGateway;
import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
//import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Sql(statements = "delete from employees")
@Slf4j
class EmployeesControllerIT {

    @Autowired
    WebTestClient webClient;

    @MockBean
    AddressesGateway addressesGateway;

    @MockBean
    EventsGateway eventsGateway;

    @Test
    //@RepeatedTest(2)
    void testCreateThanList() {
        var name = "John Doe " + UUID.randomUUID().toString();
        log.info("Name: {}", name);
        webClient.post()
                .uri("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue("""
//                        {"name": "John Doe"}
//                        """)
                .bodyValue(new CreateEmployeeCommand(name))
                .exchange()
                .expectStatus().isCreated()
//                .expectBody().jsonPath(".['name']", "John Doe");
                .expectBody(EmployeeDto.class).value(e -> assertEquals(name, e.getName()));


        webClient.get()
                .uri("/api/employees")
                .exchange()
                .expectBodyList(EmployeeDto.class)
                    .value(employees -> assertThat(employees)
                            .extracting(EmployeeDto::getName)
                            .contains(name)
                    );
    }
}
