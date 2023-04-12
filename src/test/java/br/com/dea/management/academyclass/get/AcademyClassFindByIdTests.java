package br.com.dea.management.academyclass.get;

import br.com.dea.management.academyclass.AcademyClassTestUtils;
import br.com.dea.management.academyclass.AcademyClassType;
import br.com.dea.management.academyclass.domain.AcademyClass;
import br.com.dea.management.academyclass.repository.AcademyClassRepository;
import br.com.dea.management.employee.EmployeeTestUtils;
import br.com.dea.management.employee.domain.Employee;
import br.com.dea.management.employee.repository.EmployeeRepository;
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
public class AcademyClassFindByIdTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AcademyClassTestUtils academyClassTestUtils;

    @Autowired
    private AcademyClassRepository academyClassRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void whenRequestingAnExistentAcademyClassById_thenReturnTheAcademyClassSuccessfully() throws Exception {
        this.academyClassRepository.deleteAll();
        this.employeeRepository.deleteAll();

        AcademyClassType academyClassType = AcademyClassType.DEVELOPER;
        LocalDate startDate = LocalDate.of(2021, 3,1);
        LocalDate endDate = LocalDate.of(2022, 12, 4);
        this.academyClassTestUtils.createFakeAcademyClass(academyClassType, startDate, endDate);
        AcademyClass academyClass = this.academyClassRepository.findAll().get(0);

        mockMvc.perform(get("/academy-class/" + academyClass.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.startDate", is("2021-03-01")))
                .andExpect(jsonPath("$.endDate", is("2022-12-04")))
                .andExpect(jsonPath("$.instructor.name", is("name 0")));
    }

    @Test
    void whenRequestingByIdAndIdIsNotANumber_thenReturnTheBadRequestError() throws Exception {
        mockMvc.perform(get("/academy-class/xx"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(1)));
    }

    @Test
    void whenRequestingAnNonExistentAcademyClassById_thenReturnTheNotFoundError() throws Exception {
        mockMvc.perform(get("/academy-class/5000"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(1)));

    }
}
