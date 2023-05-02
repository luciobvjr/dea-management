package br.com.dea.management.project.service;

import br.com.dea.management.employee.domain.Employee;
import br.com.dea.management.employee.repository.EmployeeRepository;
import br.com.dea.management.exceptions.NotFoundException;
import br.com.dea.management.project.domain.Project;
import br.com.dea.management.project.dto.CreateProjectRequestDto;
import br.com.dea.management.project.dto.UpdateProjectRequestDto;
import br.com.dea.management.project.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Page<Project> findAllProjectsPaginated(Integer page, Integer pageSize) {
        return this.projectRepository.findAllPaginated(PageRequest.of(page, pageSize, Sort.by("startDate").ascending()));
    }

    public Project findProjectById(Long id) {
        return this.projectRepository.findById(id).orElseThrow(() -> new NotFoundException(Project.class, id));
    }

    public Project createProject(CreateProjectRequestDto createProjectRequestDto) {
        Employee productOwner = this.employeeRepository.findById(createProjectRequestDto.getProductOwner())
                .orElseThrow(() -> new NotFoundException(Project.class, createProjectRequestDto.getProductOwner()));

        Employee scrumMaster = this.employeeRepository.findById(createProjectRequestDto.getScrumMaster())
                .orElseThrow(() -> new NotFoundException(Project.class, createProjectRequestDto.getScrumMaster()));

        Project project = Project.builder()
                .startDate(createProjectRequestDto.getStartDate())
                .endDate(createProjectRequestDto.getEndDate())
                .client(createProjectRequestDto.getClient())
                .productOwner(productOwner)
                .scrumMaster(scrumMaster)
                .externalProductManager(createProjectRequestDto.getExternalProductManager())
                .build();

        return this.projectRepository.save(project);
    }

    public Project updateProject(Long projectId, UpdateProjectRequestDto updateProjectRequestDto) {
        Employee productOwner = this.employeeRepository.findById(updateProjectRequestDto.getProductOwner())
                .orElseThrow(() -> new NotFoundException(Project.class, updateProjectRequestDto.getProductOwner()));

        Employee scrumMaster = this.employeeRepository.findById(updateProjectRequestDto.getScrumMaster())
                .orElseThrow(() -> new NotFoundException(Project.class, updateProjectRequestDto.getScrumMaster()));

        Project project = this.findProjectById(projectId);

        project.setStartDate(updateProjectRequestDto.getStartDate());
        project.setEndDate(updateProjectRequestDto.getEndData());
        project.setClient(updateProjectRequestDto.getClient());
        project.setProductOwner(productOwner);
        project.setScrumMaster(scrumMaster);
        project.setExternalProductManager(updateProjectRequestDto.getExternalProductManager());

        return this.projectRepository.save(project);
    }

    public void deleteProject(Long id) {
        Project project = this.findProjectById(id);
        this.projectRepository.delete(project);
    }
}
