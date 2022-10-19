package employees;

import auditing.AuditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeesServiceTest {

    @Mock
    EmployeesRepository employeesRepository;

    @Mock
    AuditService auditService;

    EmployeesService employeesService;

    @BeforeEach
    void init() {
        employeesService = new EmployeesService(employeesRepository, new EmployeesMapperImpl(), auditService);
    }

    @Test
    void testSave() {
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
