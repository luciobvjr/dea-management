package br.com.dea.management.project.get;

import br.com.dea.management.academyclass.AcademyClassType;
import br.com.dea.management.academyclass.domain.AcademyClass;
import br.com.dea.management.employee.repository.EmployeeRepository;
import br.com.dea.management.project.ProjectTestUtils;
import br.com.dea.management.project.domain.Project;
import br.com.dea.management.project.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
public class ProjectGetByIdTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProjectTestUtils projectTestUtils;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void whenRequestingAnExistentProjectById_thenReturnTheProjectSuccessfully() throws Exception {
        this.projectRepository.deleteAll();
        this.employeeRepository.deleteAll();

        LocalDate startDate = LocalDate.of(2021, 3,1);
        LocalDate endDate = LocalDate.of(2022, 12, 4);
        Project project = this.projectTestUtils.createFakeProject(startDate, endDate);

        mockMvc.perform(get("/project/" + project.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.startDate", is("2021-03-01")))
                .andExpect(jsonPath("$.endDate", is("2022-12-04")))
                .andExpect(jsonPath("$.scrumMaster.name", is(project.getScrumMaster().getUser().getName())))
                .andExpect(jsonPath("$.productOwner.name", is(project.getProductOwner().getUser().getName())))
                .andExpect(jsonPath("$.externalProductManager", is(project.getExternalProductManager())));
    }

    @Test
    void whenRequestingByIdAndIdIsNotANumber_thenReturnTheBadRequestError() throws Exception {
        mockMvc.perform(get("/project/xx"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(1)));
    }

    @Test
    void whenRequestingAnNonExistentProjectById_thenReturnTheNotFoundError() throws Exception {
        mockMvc.perform(get("/academy-class/5000"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(1)));

    }
}
