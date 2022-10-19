package employees;

import auditing.Auditable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeCommand implements Auditable {

    @NotBlank
    @Schema(description = "the name of the employee", example = "John Doe")
    private String name;
}
