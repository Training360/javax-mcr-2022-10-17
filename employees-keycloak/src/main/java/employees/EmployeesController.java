package employees;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@AllArgsConstructor
public class EmployeesController {

    private EmployeesService employeesService;

    private ApplicationUris applicationUris;

    @GetMapping
    public List<EmployeeDto> listEmployees() {
        return employeesService.listEmployees();
    }

    @GetMapping("/{id}")
    public EmployeeDetailsDto findEmployeeById(@PathVariable long id) {
        return employeesService.findEmployeeById(id);
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@Valid @RequestBody CreateEmployeeCommand command,
                                                      UriComponentsBuilder uri) {
        var employee = employeesService.createEmployee(command);
        return ResponseEntity
                .created(applicationUris.getUriForEmployee(uri, employee.getId()))
                .body(employee);
    }

}
