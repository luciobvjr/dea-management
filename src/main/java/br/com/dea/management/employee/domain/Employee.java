package br.com.dea.management.employee.domain;

import br.com.dea.management.employee.EmployeeType;
import br.com.dea.management.position.domain.Position;
import br.com.dea.management.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.ISBN;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Enumerated(EnumType.STRING)
    private EmployeeType employeeType;

    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "position_id")
    private Position position;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
