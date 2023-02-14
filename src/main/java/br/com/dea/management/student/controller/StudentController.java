package br.com.dea.management.student.controller;

import br.com.dea.management.student.domain.Student;
import br.com.dea.management.student.dto.StudentDto;
import br.com.dea.management.student.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class StudentController {
    @Autowired
    StudentService studentService;

    @RequestMapping(value = "student/all", method = RequestMethod.GET)
    public List<Student> getAllRawStudents() {
        return this.studentService.findAllStudents();
    }

    @GetMapping("/student/without-pagination")
    public List<StudentDto> getAllStudentsWithoutPagination() {
        List<Student> students = this.studentService.findAllStudents();
        return StudentDto.fromStudents(students);
    }

    @GetMapping("student")
    public Page<StudentDto> getStudents(@RequestParam() Integer page,
                                        @RequestParam() Integer pageSize) {
        log.info(String.format("Fetching students - page: %s | pageSize: %s", page, pageSize));

        Page<Student> studentsPaged = this.studentService.findAllStudentsPaginated(page, pageSize);
        Page<StudentDto> students = studentsPaged.map(StudentDto::fromStudent);

        log.info(String.format("Students loaded successfully - Students: %s | pageSize: %s", students.getContent(), pageSize));
        return students;
    }

    @GetMapping("/student/{id}")
    public StudentDto getStudents(@PathVariable Long id) {
        log.info(String.format("Fetching student by id : Id : %s", id));

        StudentDto student = StudentDto.fromStudent(this.studentService.findById(id));

        log.info(String.format("Student loaded successfully : Student : %s", student));

        return student;
    }
}
