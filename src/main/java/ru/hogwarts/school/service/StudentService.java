package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student deleteStudent(long id) {
        if (studentRepository.findById(id).isEmpty()) {
            return null;
        }
        final Student student = studentRepository.findById(id).get();
        studentRepository.deleteById(id);
        return student;
    }

    public Student updateStudent(Student student) {
        if (studentRepository.findById(student.getId()).isEmpty()) {
            return null;
        }
        return studentRepository.save(student);
    }

    public Collection<Student> studentByAge(int age) {
        return studentRepository.findStudentsByAge(age);
    }
}
