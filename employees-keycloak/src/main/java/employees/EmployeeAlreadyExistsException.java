package employees;

import lombok.Value;

@Value
public class EmployeeAlreadyExistsException extends RuntimeException {

    private String name;
    public EmployeeAlreadyExistsException(String name) {
        super("Employee exist with name " + name);
        this.name = name;
    }
}
