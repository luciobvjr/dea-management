package br.com.dea.management.project.update;

import br.com.dea.management.employee.EmployeeTestUtils;
import br.com.dea.management.employee.domain.Employee;
import br.com.dea.management.employee.repository.EmployeeRepository;
import br.com.dea.management.project.ProjectTestUtils;
import br.com.dea.management.project.domain.Project;
import br.com.dea.management.project.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProjectUpdateSuccessCaseTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ProjectTestUtils projectTestUtils;

    @Autowired
    EmployeeTestUtils employeeTestUtils;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
                                                                MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    @Test
    void whenRequestingProjectUpdateWithAValidPayload_thenUpdateProjectSuccessfully() throws Exception {
        this.projectRepository.deleteAll();
        this.employeeRepository.deleteAll();

        LocalDate startDate = LocalDate.of(2021, 3, 5);
        LocalDate endDate = LocalDate.of(2022, 12, 5);
        Project projectBase = this.projectTestUtils.createFakeProject(startDate, endDate);

        this.employeeTestUtils.createFakeEmployees(2);
        Employee productOwner = this.employeeRepository.findAll().get(0);
        Employee scrumMaster = this.employeeRepository.findAll().get(1);

        String payload = "{" +
                "\"startDate\": \"2021-03-01\"," +
                "\"endDate\": \"2023-02-10\"," +
                "\"client\": \"Client Updated\"," +
                "\"productOwner\": " + productOwner.getId() + "," +
                "\"scrumMaster\": " + scrumMaster.getId() + "," +
                "\"externalProductManager\": \"Michael Updated\"" +
                "}";

        mockMvc.perform(put("/project/" + projectBase.getId())
                        .contentType(APPLICATION_JSON_UTF8).content(payload))
                        .andExpect(status().isOk());

        Project project = this.projectRepository.findAll().get(0);

        assertThat(project.getStartDate()).isEqualTo("2021-03-01");
        assertThat(project.getEndDate()).isEqualTo("2023-02-10");
        assertThat(project.getClient()).isEqualTo("Client Updated");
        assertThat(project.getProductOwner().getUser().getName()).isEqualTo("name 0");
        assertThat(project.getScrumMaster().getUser().getName()).isEqualTo("name 1");
        assertThat(project.getExternalProductManager()).isEqualTo("Michael Updated");
    }
}
