package employees;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeesControllerTest {

    @Mock
    EmployeesService service;

    @Mock
    UriComponentsBuilder builder;

    @InjectMocks
    EmployeesController controller;

    @Test
    void createEmployee() {
        // Given - arrange
        when(service.createEmployee(any())).thenReturn(new EmployeeDto(1L, "John Doe"));

        // When - act
        UriComponents components = mock(UriComponents.class);
        when(builder.path(any())).thenReturn(builder);
        when(builder.buildAndExpand(anyLong())).thenReturn(components);

        var employee = controller.createEmployee(new CreateEmployeeCommand("John Doe"), builder
                ).getBody();

        // Then - assert
        verify(service)
                .createEmployee(argThat(c -> c.getName().equals("John Doe")));
        assertEquals("John Doe", employee.getName());
        assertEquals(1L, employee.getId());
    }
}