package employees;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeesService {

    private EmployeesRepository repository;

    public EmployeeDto createEmployee(CreateEmployeeCommand command) {
        var employee = new Employee(command.getName());
        repository.save(employee);
        return new EmployeeDto(employee.getId(), employee.getName());
    }

    public EmployeeDto findEmployeeById(long id) {
        var employee = repository
                .findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
        return new EmployeeDto(employee.getId(), employee.getName());
    }

    public List<EmployeeDto> listEmployees() {
        return repository.findAll()
                .stream().map(e -> new EmployeeDto(e.getId(), e.getName())).toList();
    }

}
