package br.com.dea.management.employee.dto;

import br.com.dea.management.employee.EmployeeType;
import br.com.dea.management.employee.domain.Employee;
import br.com.dea.management.position.domain.Position;
import br.com.dea.management.position.domain.PositionDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class EmployeeDto {
    private Long id;
    private String name;
    private String email;
    private String linkedin;
    private EmployeeType employeeType;
    @JsonProperty("position")
    private PositionDto positionDto;

    public static List<EmployeeDto> fromEmployees(List<Employee> students) {
        return students.stream().map(EmployeeDto::fromEmployee).collect(Collectors.toList());
    }

    public static EmployeeDto fromEmployee(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setName(employee.getUser().getName());
        employeeDto.setEmail(employee.getUser().getEmail());
        employeeDto.setLinkedin(employee.getUser().getLinkedin());
        employeeDto.setEmployeeType(employee.getEmployeeType());

        Position position = employee.getPosition();

        employeeDto.setPositionDto(new PositionDto(position.getId(), position.getDescription(), position.getSeniority()));

        return employeeDto;
    }
}