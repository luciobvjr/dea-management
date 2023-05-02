package br.com.dea.management.project.controller;

import br.com.dea.management.project.domain.Project;
import br.com.dea.management.project.dto.CreateProjectRequestDto;
import br.com.dea.management.project.dto.ProjectDto;
import br.com.dea.management.project.dto.UpdateProjectRequestDto;
import br.com.dea.management.project.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@Tag(name = "Project", description = "The Project API")
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @Operation(summary = "Load the list of projects paginated.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Page or Page Size params not valid"),
            @ApiResponse(responseCode = "500", description = "Error fetching project list"),
    })
    @GetMapping("project")
    public Page<ProjectDto> getProject(@RequestParam Integer page,
                                    @RequestParam Integer pageSize) {
        log.info(String.format("Fetching project : page : %s : pageSize : %s", page, pageSize));
        Page<Project> projects = projectService.findAllProjectsPaginated(page, pageSize);
        log.info("AcademyClasses loaded successfully");

        return projects.map(ProjectDto::fromProject);
    }

    @Operation(summary = "Load project by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Project id not valid"),
            @ApiResponse(responseCode = "404", description = "Project not found"),
            @ApiResponse(responseCode = "500", description = "Error fetching project list"),
    })
    @GetMapping("project/{id}")
    public ProjectDto getProject(@PathVariable Long id) {
        log.info(String.format("Fetching project with id : %s ", id));
        ProjectDto project = ProjectDto.fromProject(this.projectService.findProjectById(id));
        log.info(String.format("AcademyClass loaded successfully with id : %s", project.getId()));

        return project;
    }

    @Operation(summary = "Create a new project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Payload not valid"),
            @ApiResponse(responseCode = "500", description = "Error creating project")
    })
    @PostMapping("project")
    public void createProject(@Valid @RequestBody CreateProjectRequestDto createProjectRequestDto) {
        log.info(String.format("Creating project : Payload : %s", createProjectRequestDto));

        Project project = projectService.createProject(createProjectRequestDto);

        log.info(String.format("Project created successfully : id : %s", project.getId()));
    }

    @Operation(summary = "Update an project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Payload not valid"),
            @ApiResponse(responseCode = "404", description = "Project not found"),
            @ApiResponse(responseCode = "500", description = "Error updating project")
    })
    @PutMapping("project/{projectId}")
    public void updateProject(@PathVariable Long projectId,@Valid @RequestBody UpdateProjectRequestDto updateProjectRequestDto) {
        log.info(String.format("Updating project : Payload : %s", updateProjectRequestDto));

        Project project = projectService.updateProject(projectId, updateProjectRequestDto);

        log.info(String.format("Project updated successfully : id : %s", project.getId()));
    }
}
