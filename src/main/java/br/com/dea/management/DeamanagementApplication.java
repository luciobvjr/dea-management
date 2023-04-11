package br.com.dea.management;

import br.com.dea.management.position.domain.Position;
import br.com.dea.management.position.repository.PositionRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

	Position position = Position.builder()
			.seniority("Dev iOS senior")
			.description("Senior iOS developer with XCode")
			.build();

	@Override
	public void run(String... args) {
		this.positionRepository.save(position);
	}
}
