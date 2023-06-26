package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> find(@PathVariable long id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("/color/{color}")
    public Collection<Faculty> getFaculty(@PathVariable String color) {
        return facultyService.facultyByColor(color);
    }

    @GetMapping
    public Collection<Faculty> getFacultyByNameOrColor(String name, String color) {
        return facultyService.facultyByNameOrColor(name, color);
    }

    @GetMapping("/{faculty}/students")
    public Collection<Student> getStudents(@PathVariable String faculty){
        return facultyService.getStudentByFaculty(faculty);
    }

    @PostMapping
    public Faculty create(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> update(@RequestBody Faculty faculty) {
        Faculty facultyUpdate = facultyService.updateFaculty(faculty);
        if (facultyUpdate == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultyUpdate);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> delete(@PathVariable long id) {
        Faculty facultyDelete = facultyService.deleteFaculty(id);
        if (facultyDelete == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultyDelete);
    }

}
