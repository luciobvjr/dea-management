package br.com.dea.management.academyclass;

import br.com.dea.management.academyclass.domain.AcademyClass;
import br.com.dea.management.academyclass.repository.AcademyClassRepository;
import br.com.dea.management.employee.EmployeeTestUtils;
import br.com.dea.management.employee.domain.Employee;
import br.com.dea.management.employee.repository.EmployeeRepository;
import br.com.dea.management.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class AcademyClassTestUtils {
    @Autowired
    public AcademyClassRepository academyClassRepository;
    @Autowired
    private EmployeeTestUtils employeeTestUtils;
    @Autowired
    private EmployeeRepository employeeRepository;

    public void createFakeAcademyClass(AcademyClassType academyClassType, LocalDate startDate, LocalDate endDate) {
        employeeTestUtils.createFakeEmployees(1);

        AcademyClass academyClass = AcademyClass.builder()
                .academyClassType(academyClassType)
                .startDate(startDate)
                .endDate(endDate)
                .instructor(this.employeeRepository.findAll().get(0))
                .build();

        this.academyClassRepository.save(academyClass);
    }
}
