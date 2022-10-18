package employees;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmployeesMapper {

    EmployeeDto toDto(Employee employee);

    List<EmployeeDto> toDto(List<Employee> employees);
}
