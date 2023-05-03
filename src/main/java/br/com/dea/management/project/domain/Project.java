package br.com.dea.management.project.domain;

import br.com.dea.management.employee.domain.Employee;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Column
    private String client;

    @OneToOne
    @JoinColumn(name = "productOwner_id")
    private Employee productOwner;

    @OneToOne
    @JoinColumn(name = "scrumMaster_id")
    private Employee scrumMaster;

    @Column
    private String externalProductManager;
}
