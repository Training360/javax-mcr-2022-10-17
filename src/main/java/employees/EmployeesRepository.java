package employees;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeesRepository extends JpaRepository<Employee, Long> {

    @Query("select new employees.EmployeeDto(e.id, e.name) from Employee e where e.id = :id")
    Optional<EmployeeDto> findDtoById(long id);

    @Query("select new employees.EmployeeDto(e.id, e.name) from Employee e")
    List<EmployeeDto> findAllDto();

    Optional<Employee> findEmployeeByNameIgnoreCase(String name);
}
