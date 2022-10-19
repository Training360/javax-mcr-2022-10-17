package employees;

import employees.addressesgateway.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmployeesMapper {

    EmployeeDto toDto(Employee employee);

    List<EmployeeDto> toDto(List<Employee> employees);

    AddressDto toDto(Address address);

    @Mapping(target = "address", source = "address")
    EmployeeDetailsDto toDto(EmployeeDto employeeDto, Address address);
}
