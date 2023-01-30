package br.com.dea.management.student.controller;

import br.com.dea.management.student.domain.Student;
import br.com.dea.management.student.dto.StudentDto;
import br.com.dea.management.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
    public Page<StudentDto> getStudents(@RequestParam Integer page,
                                        @RequestParam Integer pageSize) {
        Page<Student> studentsPaged = this.studentService.findAllStudentsPaginated(page, pageSize);
        return studentsPaged.map(StudentDto::fromStudent);
    }
}
