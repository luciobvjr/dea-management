package br.com.dea.management.academyclass.update;

import br.com.dea.management.academyclass.AcademyClassTestUtils;
import br.com.dea.management.academyclass.AcademyClassType;
import br.com.dea.management.academyclass.domain.AcademyClass;
import br.com.dea.management.academyclass.repository.AcademyClassRepository;
import br.com.dea.management.employee.EmployeeTestUtils;
import br.com.dea.management.employee.domain.Employee;
import br.com.dea.management.employee.repository.EmployeeRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AcademyClassUpdatePayloadValidationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AcademyClassRepository academyClassRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AcademyClassTestUtils academyClassTestUtils;
    @Autowired
    private EmployeeTestUtils employeeTestUtils;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    @Test
    void whenPayloadHasRequiredFieldsAreMissing_thenReturn400AndTheErrors() throws Exception {
        String payload = "{}";
        mockMvc.perform(put("/academy-class/1")
                        .contentType(APPLICATION_JSON_UTF8).content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(4)))
                .andExpect(jsonPath("$.details[*].field", hasItem("startDate")))
                .andExpect(jsonPath("$.details[*].errorMessage", hasItem("Start Date could not be null")))
                .andExpect(jsonPath("$.details[*].field", hasItem("endDate")))
                .andExpect(jsonPath("$.details[*].errorMessage", hasItem("End Date could not be null")))
                .andExpect(jsonPath("$.details[*].field", hasItem("academyClassType")))
                .andExpect(jsonPath("$.details[*].errorMessage", hasItem("Academy Class Type could not be null")))
                .andExpect(jsonPath("$.details[*].field", hasItem("instructorId")))
                .andExpect(jsonPath("$.details[*].errorMessage", hasItem("Instructor Id could not be null")));
    }

    @Test
    void whenEditingAcademyClassThatDoesNotExists_thenReturn404() throws Exception {
        this.academyClassRepository.deleteAll();
        this.employeeRepository.deleteAll();
        this.employeeTestUtils.createFakeEmployees(1);

        Employee instructor = this.employeeRepository.findAll().get(0);

        String payload = "{" +
                "\"startDate\": \"2021-03-01\"," +
                "\"endDate\": \"2022-12-10\"," +
                "\"academyClassType\": \"DEVELOPER\"," +
                "\"instructorId\": " + instructor.getId() +
                "}";

        mockMvc.perform(put("/academy-class/1")
                        .contentType(APPLICATION_JSON_UTF8).content(payload))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(1)));
    }

    @Test
    void whenEditingAcademyClassWithEmployeeThatDoesNotExists_thenReturn404() throws Exception {
        this.academyClassRepository.deleteAll();
        this.employeeRepository.deleteAll();

        LocalDate startDate = LocalDate.of(2021, 3, 5);
        LocalDate endDate = LocalDate.of(2022, 12, 5);
        this.academyClassTestUtils.createFakeAcademyClass(AcademyClassType.DEVELOPER, startDate, endDate);

        AcademyClass academyClass = academyClassRepository.findAll().get(0);

        String payload = "{" +
                "\"startDate\": \"2021-03-01\"," +
                "\"endDate\": \"2022-12-10\"," +
                "\"academyClassType\": \"DEVELOPER\"," +
                "\"instructorId\": " + 0 +
                "}";

        mockMvc.perform(put("/academy-class/1")
                        .contentType(APPLICATION_JSON_UTF8).content(payload))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(1)));
    }
}
