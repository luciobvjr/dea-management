package br.com.dea.management.academyclass.update;

import br.com.dea.management.academyclass.AcademyClassTestUtils;
import br.com.dea.management.academyclass.AcademyClassType;
import br.com.dea.management.academyclass.domain.AcademyClass;
import br.com.dea.management.academyclass.repository.AcademyClassRepository;
import br.com.dea.management.employee.EmployeeTestUtils;
import br.com.dea.management.employee.domain.Employee;
import br.com.dea.management.employee.repository.EmployeeRepository;
import br.com.dea.management.position.domain.Position;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static br.com.dea.management.employee.EmployeeType.INSTRUCTOR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AcademyClassUpdateSuccessCaseTests {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    AcademyClassRepository academyClassRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    AcademyClassTestUtils academyClassTestUtils;
    @Autowired
    EmployeeTestUtils employeeTestUtils;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    @Test
    void whenRequestingAcademyClassUpdateWithAValidPayload_thenUpdateAcademyClassSuccessfully() throws Exception {
        this.academyClassRepository.deleteAll();

        LocalDate startDate = LocalDate.of(2021, 3, 5);
        LocalDate endDate = LocalDate.of(2022, 12, 5);
        this.academyClassTestUtils.createFakeAcademyClass(AcademyClassType.DEVELOPER, startDate, endDate);
        AcademyClass academyClassBase = this.academyClassRepository.findAll().get(0);

        this.employeeTestUtils.createFakeEmployees(1);
        Employee instructor = this.employeeRepository.findAll().get(this.employeeRepository.findAll().size() - 1);


        String payload = "{" +
                "\"startDate\": \"2019-03-01\"," +
                "\"endDate\": \"2020-12-10\"," +
                "\"academyClassType\": \"DESIGN\"," +
                "\"instructorId\": " + instructor.getId() +
                "}";

        mockMvc.perform(put("/academy-class/" + academyClassBase.getId())
                        .contentType(APPLICATION_JSON_UTF8).content(payload))
                .andExpect(status().isOk());

        AcademyClass academyClass = this.academyClassRepository.findAll().get(0);

        assertThat(academyClass.getStartDate()).isEqualTo("2019-03-01");
        assertThat(academyClass.getEndDate()).isEqualTo("2020-12-10");
        assertThat(academyClass.getAcademyClassType()).isEqualTo(AcademyClassType.DESIGN);
        assertThat(academyClass.getInstructor().getId()).isEqualTo(instructor.getId());
    }
}
