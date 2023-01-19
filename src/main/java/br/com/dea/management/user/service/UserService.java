package br.com.dea.management.user.service;

import br.com.dea.management.exceptions.NotFoundException;
import br.com.dea.management.user.domain.User;
import br.com.dea.management.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAllUsers() {
        return  this.userRepository.findAll();
    }

    public User findUserById(Long id) {
        Optional<User> user = this.userRepository.findById(id);

        if (user.isPresent()) {
            return user.get();
        }

        throw new NotFoundException(User.class, id);
    }

    public User findUserByName(String name) {
        Optional<User> user = this.userRepository.findByName(name);
        return user.orElseThrow(() -> new NotFoundException(User.class, name));
    }

    public User findUserByEmail(String email) {
        Optional<User> user = this.userRepository.findByEmail(email);
        return user.orElseThrow(() -> new NotFoundException(User.class, email));
    }

    public User findUserByLinkedin(String linkedin) {
        Optional<User> user = this.userRepository.findByLinkedin(linkedin);
        return user.orElseThrow(() -> new NotFoundException(User.class, linkedin));
    }
}
