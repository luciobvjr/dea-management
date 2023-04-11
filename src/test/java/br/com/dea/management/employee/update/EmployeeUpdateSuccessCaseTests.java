package br.com.dea.management.employee.update;

import br.com.dea.management.employee.EmployeeTestUtils;
import br.com.dea.management.employee.domain.Employee;
import br.com.dea.management.employee.repository.EmployeeRepository;
import br.com.dea.management.position.domain.Position;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EmployeeUpdateSuccessCaseTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeTestUtils employeeTestUtils;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    @Test
    void whenRequestingEmployeeUpdateWithAValidPayload_thenUpdateAEmployeeSuccessfully() throws Exception {
        this.employeeRepository.deleteAll();
        this.employeeTestUtils.createFakeEmployees(1);
        Position position = this.employeeTestUtils.createFakePosition("Designer", "Junior");

        Employee employeeBase = this.employeeRepository.findAll().get(0);

        String payload = "{" +
                "\"employeeType\": \"INSTRUCTOR\"," +
                "\"position\": " + position.getId() + "," +
                "\"name\": \"updated name\"," +
                "\"email\": \"updatedemail@email.com\"," +
                "\"password\": \"updatedpassword\"," +
                "\"linkedin\": \"updated linkedin\"" +
                "}";
        mockMvc.perform(put("/employee/" + employeeBase.getId())
                        .contentType(APPLICATION_JSON_UTF8).content(payload))
                .andExpect(status().isOk());

        Employee employee = this.employeeRepository.findAll().get(0);

        assertThat(employee.getEmployeeType()).isEqualTo(INSTRUCTOR);
        assertThat(employee.getPosition().getId()).isEqualTo(position.getId());
        assertThat(employee.getUser().getName()).isEqualTo("updated name");
        assertThat(employee.getUser().getEmail()).isEqualTo("updatedemail@email.com");
        assertThat(employee.getUser().getPassword()).isEqualTo("updatedpassword");
        assertThat(employee.getUser().getLinkedin()).isEqualTo("updated linkedin");
    }

}
