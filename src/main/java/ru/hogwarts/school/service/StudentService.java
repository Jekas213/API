package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    public StudentService(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student deleteStudent(long id) {
        final Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isEmpty()) {
            return null;
        }
        final Student student = optionalStudent.get();
        studentRepository.deleteById(id);
        return student;
    }

    public Student updateStudent(Student student) {
        if (studentRepository.findById(student.getId()).isEmpty()) {
            return null;
        }
        return studentRepository.save(student);
    }

    public Collection<Student> findStudentByAgeBetween(int min, int max) {
        return studentRepository.findStudentsByAgeBetween(min, max);
    }

    public Collection<Student> studentByAge(int age) {
        return studentRepository.findStudentsByAge(age);
    }

    public Faculty findFacultyByStudent(long id) {
        final String nameFaculty = studentRepository.findById(id).map(Student::getFaculty).orElse(null);
        if (nameFaculty == null) {
            return null;
        }
        return facultyRepository.findFacultyByNameIgnoreCase(nameFaculty);
    }
}
