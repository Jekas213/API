package ru.hogwarts.school.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.*;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    public StudentService(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public Student createStudent(Student student) {
        logger.info("Was invoked method for create student");
        logger.debug("the name of the student we are creating: {}", student.getName());
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        logger.info("Was invoked method for find student");
        logger.debug("id of the student we are find: {}", id);
        return studentRepository.findById(id).orElse(null);
    }

    public Student deleteStudent(long id) {
        logger.info("Was invoked method for delete student");
        logger.debug("id of the student we are deleted: {}", id);
        final Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isEmpty()) {
            return null;
        }
        final Student student = optionalStudent.get();
        studentRepository.deleteById(id);
        return student;
    }

    public Student updateStudent(Student student) {
        logger.info("Was invoked method for update student");
        logger.debug("the name of the student we are updating: {}", student.getName());
        if (studentRepository.findById(student.getId()).isEmpty()) {
            return null;
        }
        return studentRepository.save(student);
    }

    public Collection<Student> findStudentByAgeBetween(int min, int max) {
        logger.info("Was invoked method for find student by age between");
        logger.debug("min age and max age at which we are looking for a student: min = {}, max = {}", min, max);
        return studentRepository.findStudentsByAgeBetween(min, max);
    }

    public Collection<Student> studentByAge(int age) {
        logger.info("Was invoked method for find student by age");
        logger.debug("age at which we are looking for a student: age = {}", age);
        return studentRepository.findStudentsByAge(age);
    }

    public Optional<Faculty> findFacultyByStudent(long id) {
        logger.info("Was invoked method for find faculty by student");
        logger.debug("id of the student we are looking for faculty: id = {}", id);
        final Student student = studentRepository.findById(id).orElse(null);
        if (student == null) {
            return Optional.empty();
        }
        return facultyRepository.findById(student.getFaculty().getId());
    }

    public int getCountOfStudent() {
        logger.info("Was invoked method for get count of students");
        return studentRepository.getCountOfStudent();
    }

    public double getAverageAge() {
        logger.info("Was invoked method for get average age of students");
        return studentRepository.getAverageAge();
    }

    public Collection<Student> getLastStudents() {
        logger.info("Was invoked method for get last five students");
        return studentRepository.getLastFiveStudent();
    }

    public Collection<Student> getAll(Integer pageNumber, Integer pageSize) {
        logger.info("Was invoked method for get all students");
        logger.debug("Page number and page size for method get all students: page number = {}, page size = {}", pageNumber, pageSize);
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return studentRepository.findAll(pageRequest).getContent();
    }

    public List<String> getAllStudentsByNameBeginWithA(String pref) {
        logger.info("Was invoked method for get all names start with pref");
        logger.debug("name prefix: {}", pref);
        final String prefCapitalize = capitalize(pref);
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .map(StringUtils::capitalize)
                .filter(name -> name.startsWith(prefCapitalize))
                .sorted()
                .toList();

    }

    public double getAverageAgeAllStudents() {
        logger.info("Was invoked method for get average age of students (stream)");
        return studentRepository.findAll().stream()
                .mapToDouble(Student::getAge)
                .average()
                .orElse(0.0);
    }

    public void thread() {
        List<String> students = studentRepository.findAll().stream()
                .map(Student::getName)
                .toList();

        System.out.println(students.get(0));
        System.out.println(students.get(1));

        new Thread(() -> {
            System.out.println(students.get(2));
            System.out.println(students.get(3));
        }).start();

        new Thread(() -> {
            System.out.println(students.get(4));
            System.out.println(students.get(5));
        }).start();
    }

    public void threadSynchronized() {
        List<String> students = studentRepository.findAll().stream()
                .map(Student::getName)
                .toList();

        print(students.get(0), students.get(1));

        new Thread(() -> print(students.get(2), students.get(3))).start();

        new Thread(() -> print(students.get(4), students.get(5))).start();
    }

    private static synchronized void print(String name1, String name2) {
        try {
            Thread.sleep(5000);
            System.out.println(name1);
            System.out.println(name2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
