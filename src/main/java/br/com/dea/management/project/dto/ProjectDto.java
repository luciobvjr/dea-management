package br.com.dea.management.project.dto;

import br.com.dea.management.academyclass.dto.AcademyClassDto;
import br.com.dea.management.employee.dto.EmployeeDto;
import br.com.dea.management.project.domain.Project;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDto {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String client;
    private EmployeeDto productOwner;
    private EmployeeDto scrumMaster;
    private String externalProductManager;

    public static List<ProjectDto> fromProjects(List<Project> projects) {
        return projects.stream().map(ProjectDto::fromProject).collect(Collectors.toList());
    }

    public static ProjectDto fromProject(Project project) {
        return ProjectDto.builder()
                .id(project.getId())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .client(project.getClient())
                .productOwner(EmployeeDto.fromEmployee(project.getProductOwner()))
                .scrumMaster(EmployeeDto.fromEmployee(project.getScrumMaster()))
                .externalProductManager(project.getExternalProductManager())
                .build();
    }
}
