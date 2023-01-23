package br.com.dea.management;

import br.com.dea.management.user.domain.User;
import br.com.dea.management.user.repository.UserRepository;
import br.com.dea.management.user.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class DeamanagementApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DeamanagementApplication.class, args);
	}
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;
	@PersistenceContext
	private EntityManager entityManager;
	@Override
	public void run(String... args) {
		// Deleting all Users
		this.userRepository.deleteAll();

		// Creating some users
		User claudio = new User();
		claudio.setName("Claudio F.");
		claudio.setEmail("claudio@email.com");
		claudio.setLinkedin("linkedin.com/claudiof");
		claudio.setPassword("claudio'spassword");

		User erick = new User();
		erick.setName("Erick M.");
		erick.setEmail("erick@email.com");
		erick.setLinkedin("linkedin.com/erick");
		erick.setPassword("erick'spassword");

		User jiseli = new User();
		jiseli.setName("Jiseli N.");
		jiseli.setEmail("jiseli@email.com");
		jiseli.setLinkedin("linkedin.com/jiseli");
		jiseli.setPassword("jiseli'spassword");

		User lucio = new User();
		lucio.setName("Lucio V.");
		lucio.setEmail("lucio@email.com");
		lucio.setLinkedin("linkedin.com/lucio");
		lucio.setPassword("lucio'spassword");

		this.userRepository.save(claudio);
		this.userRepository.save(erick);
		this.userRepository.save(jiseli);
		this.userRepository.save(lucio);

		//Loading all Users
		List<User> users = this.userService.findAllUsers();
		users.forEach(u -> System.out.println("Name: " + u.getName()));

		//Loading by @Query
		User loadedUserByName = this.userService.findUserByName(claudio.getName());
			System.out.println("\nUser claudio (From @Query) name: " + loadedUserByName.getName());

		//Loading user by email
		User loadedUserByEmail = this.userService.findUserByEmail(erick.getEmail());
		System.out.println("User " + erick.getEmail() + " name: " + loadedUserByEmail.getName());

		TypedQuery<User> q = entityManager.createNamedQuery("linkedinQuery", User.class);
		q.setParameter("linkedin", jiseli.getLinkedin());
		User userFromNamedQuery = q.getResultList().get(0);
		System.out.println("User name from linkedin: " + jiseli.getLinkedin() + " (From NamedQuery): " + userFromNamedQuery.getName());

		//Updating user jiseli linkedin
		userFromNamedQuery.setLinkedin("linkedin.com/jiseli-new");
		this.userRepository.save(userFromNamedQuery);
	}
}
