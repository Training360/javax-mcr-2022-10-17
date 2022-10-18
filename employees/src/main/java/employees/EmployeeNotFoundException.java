package employees;

import lombok.Value;

@Value
public class EmployeeNotFoundException extends RuntimeException {

    private long employeeId;
    public EmployeeNotFoundException(long employeeId) {
        super("Employee not found with id: " + employeeId);
        this.employeeId = employeeId;
    }
}
