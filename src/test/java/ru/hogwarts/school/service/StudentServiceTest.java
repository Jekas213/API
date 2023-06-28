package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository mock;

    @InjectMocks
    private StudentService out;

    final Student student = new Student(1, "Harry", 14);

    @Test
    void createStudent() {
        when(mock.save(any())).thenReturn(student);

        assertEquals(student, out.createStudent(student));
    }

    @Test
    void findStudentCorrect() {
        when(mock.findById(1L)).thenReturn(Optional.of(student));

        assertEquals(student, out.findStudent(1));
    }

    @Test
    void findStudentInCorrect() {
        when(mock.findById(anyLong())).thenReturn(Optional.empty());

        assertNull(out.findStudent(1));
    }

    @Test
    void deleteStudentCorrect() {
        when(mock.findById(1L)).thenReturn(Optional.of(student));

        assertEquals(student, out.deleteStudent(1));
    }

    @Test
    void deleteStudentInCorrect() {
        when(mock.findById(anyLong())).thenReturn(Optional.empty());

        assertNull(out.deleteStudent(1));
    }

    @Test
    void updateStudentCorrect() {
        when(mock.findById(anyLong())).thenReturn(Optional.of(student));
        when(mock.save(any())).thenReturn(student);

        assertEquals(student, out.updateStudent(student));
    }

    @Test
    void updateStudentInCorrect() {
        when(mock.findById(anyLong())).thenReturn(Optional.empty());

        assertNull(out.updateStudent(student));
    }

    @Test
    void studentByAge() {
        Collection<Student> studentCollection = new ArrayList<>(List.of(student));

        when(mock.findStudentsByAge(14)).thenReturn(studentCollection);

        assertIterableEquals(studentCollection, out.studentByAge(14));
    }
}