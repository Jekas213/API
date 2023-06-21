package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

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

    @GetMapping("{age}")
    public List<Student> getStudent(@PathVariable int age) {
        return studentService.studentByAge(age);
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
