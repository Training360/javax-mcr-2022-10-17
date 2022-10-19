package employees;

import auditing.AuditService;
import employees.addressesgateway.AddressesGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeesService {

    private EmployeesRepository repository;

    private EmployeesMapper employeesMapper;

    private AuditService auditService;

    private AddressesGateway addressesGateway;

    public EmployeeDto createEmployee(CreateEmployeeCommand command) {
        auditService.audit(command);

        if (repository.findEmployeeByNameIgnoreCase(command.getName()).isPresent()) {
            throw new EmployeeAlreadyExistsException(command.getName());
        }

        var employee = new Employee(command.getName());
        repository.save(employee);
        return employeesMapper.toDto(employee);
    }

    public EmployeeDetailsDto findEmployeeById(long id) {
        var employee = repository.findDtoById(id).orElseThrow(() -> new EmployeeNotFoundException(id));

        var details = employeesMapper.toDto(employee);
        var address = employeesMapper.toDto(addressesGateway.getAddressForEmployee(employee.getName()));
        details.setAddress(address);

        return details;
    }

    public List<EmployeeDto> listEmployees() {
        return repository.findAllDto();
    }

}
