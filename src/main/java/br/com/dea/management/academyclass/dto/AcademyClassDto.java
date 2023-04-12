package br.com.dea.management.academyclass.dto;

import br.com.dea.management.academyclass.domain.AcademyClass;
import br.com.dea.management.employee.domain.Employee;
import br.com.dea.management.employee.dto.EmployeeDto;
import br.com.dea.management.student.domain.Student;
import br.com.dea.management.student.dto.StudentDto;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcademyClassDto {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private EmployeeDto instructor;

    public static List<AcademyClassDto> fromAcademyClasses(List<AcademyClass> academyClasses) {
        return academyClasses.stream().map(AcademyClassDto::fromAcademyClass).collect(Collectors.toList());
    }

    public static AcademyClassDto fromAcademyClass(AcademyClass academyClass) {

        return AcademyClassDto.builder()
                .id(academyClass.getId())
                .startDate(academyClass.getStartDate())
                .endDate(academyClass.getEndDate())
                .instructor(EmployeeDto.fromEmployee(academyClass.getInstructor()))
                .build();
    }
}
