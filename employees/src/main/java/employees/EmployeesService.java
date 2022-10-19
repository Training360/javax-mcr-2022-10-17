package employees;

import auditing.AuditService;
import employees.addressesgateway.AddressesGateway;
import employees.eventsgateway.EventsGateway;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeesService {

    private EmployeesRepository repository;

    private EmployeesMapper employeesMapper;

    private AuditService auditService;

    private AddressesGateway addressesGateway;

    private EventsGateway eventsGateway;

    private MeterRegistry meterRegistry;

    @PostConstruct
    public void init() {
        Counter.builder("employees.created")
                .description("The number of the created employees")
                .baseUnit("number")
                .register(meterRegistry);
    }

    public EmployeeDto createEmployee(CreateEmployeeCommand command) {
        log.info("Create employee");
        log.debug("Create employee with name {}", command.getName());

        auditService.audit(command);

        if (repository.findEmployeeByNameIgnoreCase(command.getName()).isPresent()) {
            throw new EmployeeAlreadyExistsException(command.getName());
        }

        var employee = new Employee(command.getName());
        repository.save(employee);

        meterRegistry.counter("employees.created").increment();

        eventsGateway.sendMessage("Employee has been created with name: " + command.getName());

        return employeesMapper.toDto(employee);
    }

    public EmployeeDetailsDto findEmployeeById(long id) {
        var employee = repository.findDtoById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        var address = addressesGateway.getAddressForEmployee(employee.getName());

        return employeesMapper.toDto(employee, address);
    }

    public List<EmployeeDto> listEmployees() {
        return repository.findAllDto();
    }

}
