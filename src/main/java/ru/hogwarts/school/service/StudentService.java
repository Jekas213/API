package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final Map<Long, Student> studentMap = new HashMap<>();
    private long generateId = 0;

    public Student createStudent(Student student) {
        student.setId(++generateId);
        studentMap.put(generateId, student);
        return student;
    }

    public Student findStudent(long id) {
        return studentMap.get(id);
    }

    public Student deleteStudent(long id) {
        return studentMap.remove(id);
    }

    public Student updateStudent(Student student) {
        if (studentMap.containsKey(student.getId())) {
            studentMap.put(student.getId(), student);
            return student;
        }
        return null;
    }

    public List<Student> studentByAge(int age) {
        return studentMap.values().stream()
                .filter(s -> s.getAge() == age)
                .collect(Collectors.toList());
    }
}
