package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> find(@PathVariable long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping("/age/{age}")
    public Collection<Student> getStudent(@PathVariable int age) {
        return studentService.studentByAge(age);
    }

    @GetMapping("/age")
    public Collection<Student> getStudentByAge(@RequestParam int minAge, int maxAge) {
        return studentService.findStudentByAgeBetween(minAge, maxAge);
    }

    @GetMapping("/faculty/{id}")
    public ResponseEntity<Faculty> getFacultyByStudent(@PathVariable long id){
        Faculty faculty = studentService.findFacultyByStudent(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PostMapping
    public Student create(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PutMapping
    public ResponseEntity<Student> update(@RequestBody Student student) {
        Student studentUpdate = studentService.updateStudent(student);
        if (studentUpdate == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentUpdate);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> delete(@PathVariable long id) {
        Student studentDelete = studentService.deleteStudent(id);
        if (studentDelete == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentDelete);
    }
}
