package employees;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateEmployeeCommand {

    @NotBlank
    private String name;
}
