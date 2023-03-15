package br.com.dea.management.employee.create;

import br.com.dea.management.employee.domain.Employee;
import br.com.dea.management.employee.repository.EmployeeRepository;
import br.com.dea.management.position.repository.PositionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static br.com.dea.management.employee.EmployeeType.INSTRUCTOR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EmployeeCreationSuccessCaseTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    PositionRepository positionRepository;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    @Test
    void whenRequestingEmployeeCreationWithAValidPayload_thenCreateAEmployeeSuccessfully() throws Exception {
        this.employeeRepository.deleteAll();
        this.positionRepository.deleteAll();

        String payload = "{" +
                "\"employeeType\": \"INSTRUCTOR\"," +
                "\"description\": \"description\"," +
                "\"seniority\": \"seniority\"," +
                "\"name\": \"name\"," +
                "\"email\": \"email@email.com\"," +
                "\"password\": \"password\"," +
                "\"linkedin\": \"linkedin\"" +
                "}";
        mockMvc.perform(post("/employee")
                        .contentType(APPLICATION_JSON_UTF8).content(payload))
                .andExpect(status().isOk());

        Employee employee = this.employeeRepository.findAll().get(0);

        assertThat(employee.getEmployeeType()).isEqualTo(INSTRUCTOR);
        assertThat(employee.getPosition().getDescription()).isEqualTo("description");
        assertThat(employee.getPosition().getSeniority()).isEqualTo("seniority");
        assertThat(employee.getUser().getName()).isEqualTo("name");
        assertThat(employee.getUser().getEmail()).isEqualTo("email@email.com");
        assertThat(employee.getUser().getPassword()).isEqualTo("password");
        assertThat(employee.getUser().getLinkedin()).isEqualTo("linkedin");
    }
}