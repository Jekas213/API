package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FacultyServiceTest {

    @Mock
    private FacultyRepository mock;

    @InjectMocks
    private FacultyService out;

    final Faculty faculty = new Faculty(1, "Gryffindor", "orange");

    @Test
    void createFacultyCorrect() {
        when(mock.save(any())).thenReturn(faculty);

        assertEquals(faculty, out.createFaculty(faculty));
    }

    @Test
    void findFacultyCorrect() {
        when(mock.findById(1L)).thenReturn(Optional.of(faculty));

        assertEquals(faculty, out.findFaculty(1));
    }

    @Test
    void findFacultyInCorrect() {
        when(mock.findById(anyLong())).thenReturn(Optional.empty());

        assertNull(out.findFaculty(1));
    }

    @Test
    void deleteFacultyCorrect() {
        when(mock.findById(1L)).thenReturn(Optional.of(faculty));

        assertEquals(faculty, out.deleteFaculty(1));
    }

    @Test
    void deleteFacultyInCorrect() {
        when(mock.findById(anyLong())).thenReturn(Optional.empty());

        assertNull(out.deleteFaculty(1));
    }

    @Test
    void updateFacultyCorrect() {
        when(mock.findById(anyLong())).thenReturn(Optional.of(faculty));
        when(mock.save(any())).thenReturn(faculty);

        assertEquals(faculty, out.updateFaculty(faculty));
    }

    @Test
    void updateFacultyInCorrect() {
        when(mock.findById(anyLong())).thenReturn(Optional.empty());

        assertNull(out.updateFaculty(faculty));
    }

    @Test
    void facultyByColor() {
        Collection<Faculty> facultyCollection = new ArrayList<>(List.of(faculty));

        when(mock.findFacultiesByColor("orange")).thenReturn(facultyCollection);

        assertIterableEquals(facultyCollection, out.facultyByColor("orange"));
    }
}