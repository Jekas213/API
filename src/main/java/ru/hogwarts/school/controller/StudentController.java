package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<Collection<Student>> getStudent(@PathVariable int age) {
        Collection<Student> students = studentService.studentByAge(age);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/age")
    public ResponseEntity<Collection<Student>> getStudentByAge(@RequestParam int minAge, int maxAge) {
        Collection<Student> students = studentService.findStudentByAgeBetween(minAge, maxAge);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/faculty/{id}")
    public ResponseEntity<Optional<Faculty>> getFacultyByStudent(@PathVariable long id) {
        Optional<Faculty> faculty = studentService.findFacultyByStudent(id);
        if (faculty.isEmpty()) {
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

    @GetMapping("/count")
    public int getCountOfStudent() {
        return studentService.getCountOfStudent();
    }

    @GetMapping("/average")
    public double getAverageAge() {
        return studentService.getAverageAge();
    }

    @GetMapping("/last")
    public ResponseEntity<Collection<Student>> getLastStudents() {
        Collection<Student> students = studentService.getLastStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getAll(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        Collection<Student> students = studentService.getAll(Math.abs(pageNumber), Math.abs(pageSize));
        return ResponseEntity.ok(students);
    }

    @GetMapping("/allNames")
    public ResponseEntity<List<String>> getAllStartWith(@RequestParam String pref) {
        List<String> names = studentService.getAllStudentsByNameBeginWithA(pref);
        return ResponseEntity.ok(names);
    }

    @GetMapping("/average/stream")
    public double getAverageAgeByStream() {
        return studentService.getAverageAgeAllStudents();
    }

    @GetMapping("/thread")
    public void thread() {
        studentService.thread();
    }

    @GetMapping("/thread-synchronized")
    public void threadSynchronized() {
        studentService.threadSynchronized();
    }
}
