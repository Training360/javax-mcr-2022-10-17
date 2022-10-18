package employees;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeesControllerIT {

    @Autowired
    WebTestClient webClient;

    @Test
    void testCreateThanList() {
        webClient.post()
                .uri("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue("""
//                        {"name": "John Doe"}
//                        """)
                .bodyValue(new CreateEmployeeCommand("John Doe"))
                .exchange()
                .expectStatus().isOk()
//                .expectBody().jsonPath(".['name']", "John Doe");
                .expectBody(EmployeeDto.class).value(e -> assertEquals("John Doe", e.getName()));


        webClient.get()
                .uri("/api/employees")
                .exchange()
                .expectBodyList(EmployeeDto.class)
                    .value(employees -> assertThat(employees)
                            .extracting(EmployeeDto::getName)
                            .contains("John Doe")
                    );
    }
}
