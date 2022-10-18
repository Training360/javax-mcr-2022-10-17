package employees;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeesService {

    private EmployeesRepository repository;

    private EmployeesMapper employeesMapper;

    public EmployeeDto createEmployee(CreateEmployeeCommand command) {
        var employee = new Employee(command.getName());
        repository.save(employee);
        return employeesMapper.toDto(employee);
    }

    public EmployeeDto findEmployeeById(long id) {
        var employee = repository
                .findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        return employeesMapper.toDto(employee);
    }

    public List<EmployeeDto> listEmployees() {
        return employeesMapper.toDto(repository.findAll());
    }

}
