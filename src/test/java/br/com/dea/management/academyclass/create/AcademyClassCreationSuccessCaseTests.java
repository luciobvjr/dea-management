package br.com.dea.management.academyclass.create;

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

import static br.com.dea.management.employee.EmployeeType.DEVELOPER;
import static br.com.dea.management.employee.EmployeeType.INSTRUCTOR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AcademyClassCreationSuccessCaseTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AcademyClassRepository academyClassRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeTestUtils employeeTestUtils;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    @Test
    public void whenRequestingAcademyClassCreationWithAValidPayload_thenCreateAnAcademyClassSuccessfully() throws Exception {
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

        mockMvc.perform(post("/academy-class")
                .contentType(APPLICATION_JSON_UTF8).content(payload))
                .andExpect(status().isOk());

        AcademyClass academyClass = this.academyClassRepository.findAll().get(0);

        assertThat(academyClass.getStartDate()).isEqualTo("2021-03-01");
        assertThat(academyClass.getEndDate()).isEqualTo("2022-12-10");
        assertThat(academyClass.getAcademyClassType()).isEqualTo(AcademyClassType.DEVELOPER);
        assertThat(academyClass.getInstructor().getId()).isEqualTo(instructor.getId());
    }
}
