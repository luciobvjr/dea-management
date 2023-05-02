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

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProjectUpdatePayloadValidationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ProjectTestUtils projectTestUtils;
    @Autowired
    private EmployeeTestUtils employeeTestUtils;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    @Test
    void whenPayloadRequiredFieldsAreMissing_thenReturn400AndTheErrors() throws Exception {
        String payload = "{}";
        mockMvc.perform(put("/project/1")
                        .contentType(APPLICATION_JSON_UTF8).content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(5)))
                .andExpect(jsonPath("$.details[*].field", hasItem("startDate")))
                .andExpect(jsonPath("$.details[*].errorMessage", hasItem("Start Date could not be null")))
                .andExpect(jsonPath("$.details[*].field", hasItem("endDate")))
                .andExpect(jsonPath("$.details[*].errorMessage", hasItem("End Date could not be null")))
                .andExpect(jsonPath("$.details[*].field", hasItem("client")))
                .andExpect(jsonPath("$.details[*].errorMessage", hasItem("Client could not be null")))
                .andExpect(jsonPath("$.details[*].field", hasItem("scrumMaster")))
                .andExpect(jsonPath("$.details[*].errorMessage", hasItem("Scrum master Id could not be null")))
                .andExpect(jsonPath("$.details[*].field", hasItem("productOwner")))
                .andExpect(jsonPath("$.details[*].errorMessage", hasItem("Product owner Id could not be null")));
    }

    @Test
    void whenEditingProjectThatDoesNotExists_thenReturn404() throws Exception {
        this.projectRepository.deleteAll();
        this.employeeRepository.deleteAll();
        this.employeeTestUtils.createFakeEmployees(2);

        Employee productOwner = this.employeeRepository.findAll().get(0);
        Employee scrumMaster = this.employeeRepository.findAll().get(1);

        String payload = "{" +
                "\"startDate\": \"2021-03-01\"," +
                "\"endDate\": \"2022-12-10\"," +
                "\"client\": \"Client Foo\"," +
                "\"productOwner\": " + productOwner.getId() + "," +
                "\"scrumMaster\": " + scrumMaster.getId() + "," +
                "\"externalProductManager\": \"Michael Foo\"" +
                "}";

        mockMvc.perform(put("/project/1")
                        .contentType(APPLICATION_JSON_UTF8).content(payload))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(1)));
    }

    @Test
    void whenEditingProjectWithEmployeeThatDoesNotExists_thenReturn404() throws Exception {
        this.projectRepository.deleteAll();
        this.employeeRepository.deleteAll();

        LocalDate startDate = LocalDate.of(2021, 3, 5);
        LocalDate endDate = LocalDate.of(2022, 12, 5);
        this.projectTestUtils.createFakeProject(startDate, endDate);

        String payload = "{" +
                "\"startDate\": \"2021-03-01\"," +
                "\"endDate\": \"2022-12-10\"," +
                "\"client\": \"Client Foo\"," +
                "\"productOwner\": " + 0 + "," +
                "\"scrumMaster\": " + 0 + "," +
                "\"externalProductManager\": \"Michael Foo\"" +
                "}";

        mockMvc.perform(put("/project/1")
                        .contentType(APPLICATION_JSON_UTF8).content(payload))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(1)));
    }
}
