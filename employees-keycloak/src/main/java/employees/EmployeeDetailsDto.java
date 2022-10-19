package employees;

import employees.addressesgateway.Address;
import lombok.Data;

@Data
public class EmployeeDetailsDto {

    private long id;

    private String name;

    private AddressDto address;
}
