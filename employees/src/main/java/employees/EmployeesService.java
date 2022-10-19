package employees;

import auditing.AuditService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeesService {

    private EmployeesRepository repository;

    private EmployeesMapper employeesMapper;

    private AuditService auditService;

    public EmployeeDto createEmployee(CreateEmployeeCommand command) {
        auditService.audit(command);

        if (repository.findEmployeeByNameIgnoreCase(command.getName()).isPresent()) {
            throw new EmployeeAlreadyExistsException(command.getName());
        }

        var employee = new Employee(command.getName());
        repository.save(employee);
        return employeesMapper.toDto(employee);
    }

    public EmployeeDto findEmployeeById(long id) {
        return repository.findDtoById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public List<EmployeeDto> listEmployees() {
        return repository.findAllDto();
    }

}
