package employees;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class EmployeesService {

    private final List<Employee> employees = Collections.synchronizedList(new ArrayList<>());

    private AtomicLong idGenerator = new AtomicLong();

    public EmployeeDto createEmployee(CreateEmployeeCommand command) {
        var employee = new Employee(idGenerator.incrementAndGet(), command.getName());
        employees.add(employee);
        return new EmployeeDto(employee.getId(), employee.getName());
    }

    public List<EmployeeDto> listEmployees() {
        return employees
                .stream().map(e -> new EmployeeDto(e.getId(), e.getName()))
                .toList();
    }

}
