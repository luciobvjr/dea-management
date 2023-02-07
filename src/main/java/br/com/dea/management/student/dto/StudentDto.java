package br.com.dea.management.student.dto;

import br.com.dea.management.student.domain.Student;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class StudentDto {
    private String name;
    private String email;
    private String linkedin;
    private String university;
    private String graduation;
    private LocalDate finish_date;

    public static StudentDto fromStudent(Student student) {
        StudentDto studentDto = new StudentDto();
        studentDto.setName(student.getUser().getName());
        studentDto.setEmail(student.getUser().getEmail());
        studentDto.setLinkedin(student.getUser().getLinkedin());
        studentDto.setUniversity(student.getUniversity());
        studentDto.setGraduation(student.getGraduation());
        studentDto.setFinish_date(student.getFinishDate());
        return studentDto;
    }

    public static List<StudentDto> fromStudents(List<Student> students) {
        return students.stream().map(StudentDto::fromStudent).collect(Collectors.toList());
    }
}
