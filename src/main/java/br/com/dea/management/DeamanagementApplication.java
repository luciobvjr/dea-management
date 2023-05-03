package br.com.dea.management;

import br.com.dea.management.employee.EmployeeType;
import br.com.dea.management.employee.domain.Employee;
import br.com.dea.management.employee.repository.EmployeeRepository;
import br.com.dea.management.position.domain.Position;
import br.com.dea.management.position.repository.PositionRepository;
import br.com.dea.management.project.domain.Project;
import br.com.dea.management.project.repository.ProjectRepository;
import br.com.dea.management.user.domain.User;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Dea Management", version = "1.0", description = "Dea Management API Description"),
		           servers = {
						   @Server(url = "http://localhost:8082${server.servlet.contextPath}", description = "Local environment URL"),
						   @Server(url = "https://deamanagement.com.br${server.servlet.contextPath}", description = "Development environment URL")
				   })
public class DeamanagementApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DeamanagementApplication.class, args);
	}

	@Autowired
	PositionRepository positionRepository;

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	Position position = Position.builder()
			.seniority("Dev iOS senior")
			.description("Senior iOS developer with XCode")
			.build();

	Project project = Project.builder()
			.startDate(LocalDate.of(2019, 5, 23))
			.endDate(LocalDate.of(2024, 9, 4))
			.build();

	@Override
	public void run(String... args) {
		User user = User.builder()
				.name("Test user")
				.email("test@mail.com")
				.password("password")
				.linkedin("linkendin.com/testuser")
				.build();

		Employee employee = Employee.builder()
				.user(user)
				.position(position)
				.employeeType(EmployeeType.DESIGNER)
				.build();

		Project project = Project.builder()
				.startDate(LocalDate.of(2019, 5, 23))
				.endDate(LocalDate.of(2024, 9, 4))
				.client("UNICAMP")
				.productOwner(employee)
				.scrumMaster(employee)
				.externalProductManager("Claudio Faustino")
				.build();

		this.positionRepository.save(position);
		this.employeeRepository.save(employee);
		this.projectRepository.save(project);
	}
}
