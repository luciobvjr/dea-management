package br.com.dea.management.project;

import br.com.dea.management.employee.EmployeeTestUtils;
import br.com.dea.management.employee.repository.EmployeeRepository;
import br.com.dea.management.project.domain.Project;
import br.com.dea.management.project.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ProjectTestUtils {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private EmployeeTestUtils employeeTestUtils;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Project createFakeProject(LocalDate startDate, LocalDate endDate) {
        employeeTestUtils.createFakeEmployees(2);

        Project project = Project.builder()
                .startDate(startDate)
                .endDate(endDate)
                .client("cliente test")
                .scrumMaster(this.employeeRepository.findAll().get(0))
                .productOwner(this.employeeRepository.findAll().get(1))
                .externalProductManager("external pm test")
                .build();

        return this.projectRepository.save(project);
    }
}
