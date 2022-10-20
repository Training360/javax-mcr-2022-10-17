package employees;

import auditing.AuditService;
import employees.addressesgateway.AddressesGateway;
import employees.eventsgateway.EventsGateway;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeesServiceTest {

    @Mock
    EmployeesRepository employeesRepository;

    @Mock
    AuditService auditService;

    @Mock
    AddressesGateway addressesGateway;

    @Mock
    EventsGateway eventsGateway;

    @Mock
    MeterRegistry meterRegistry;

    EmployeesService employeesService;

    @BeforeEach
    void init() {

        employeesService = new EmployeesService(employeesRepository, new EmployeesMapperImpl(), auditService, addressesGateway, eventsGateway, meterRegistry);
    }

    @Test
    void testSave() {
        when(meterRegistry.counter(anyString())).thenReturn(mock(Counter.class));
        employeesService.createEmployee(new CreateEmployeeCommand("John Doe"));

        verify(employeesRepository).save(argThat(e -> e.getName().equals("John Doe")));
    }

    @Test
    void testExists() {
        when(employeesRepository.findEmployeeByNameIgnoreCase(eq("John Doe"))).thenReturn(Optional.of(new Employee(1L, "John Doe")));
        var ex = assertThrows(EmployeeAlreadyExistsException.class, () ->
                 employeesService.createEmployee(new CreateEmployeeCommand("John Doe")));
        assertEquals("John Doe", ex.getName());
    }

}
