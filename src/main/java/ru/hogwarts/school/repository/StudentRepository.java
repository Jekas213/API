package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface  StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findStudentsByAge(int age);
    Collection<Student> findStudentsByAgeBetween(int min, int max);

    Collection<Student> findStudentsByFaculty_Id(long id);
    @Query(value = "SELECT count(s) FROM Student s")
    int getCountOfStudent();

    @Query(value = "SELECT avg(s.age) FROM Student s")
    double getAverageAge();
    @Query(value = "SELECT * FROM student ORDER BY id desc LIMIT 5", nativeQuery = true)
    Collection<Student> getLastFiveStudent();

}
