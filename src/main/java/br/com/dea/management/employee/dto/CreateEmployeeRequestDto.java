package br.com.dea.management.employee.dto;

import br.com.dea.management.employee.EmployeeType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateEmployeeRequestDto {
    private EmployeeType employeeType;

    private Long position;

    @NotNull(message = "Name could not be null")
    private String name;

    @NotNull(message = "Email could not be null")
    @Email(message = "Email with wrong format")
    private String email;

    @NotNull(message = "Password could not be null")
    @Size(message = "Password must have more than 8 characters", min = 8)
    private String password;

    private String linkedin;
}