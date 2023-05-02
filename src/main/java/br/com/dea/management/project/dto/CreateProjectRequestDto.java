package br.com.dea.management.project.dto;

import br.com.dea.management.employee.domain.Employee;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProjectRequestDto {
    @NotNull(message = "Start Date could not be null")
    private LocalDate startDate;

    @NotNull(message = "End Date could not be null")
    private LocalDate endData;

    @NotNull(message = "Client could not be null")
    private String client;

    @NotNull(message = "Product owner Id could not be null")
    private Long productOwner;

    @NotNull(message = "Scrum master Id could not be null")
    private Long scrumMaster;

    private String externalProductManager;
}
