package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    public FacultyService(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty deleteFaculty(long id) {
        final Optional<Faculty> optionalFaculty = facultyRepository.findById(id);
        if (optionalFaculty.isEmpty()) {
            return null;
        }
        final Faculty faculty = optionalFaculty.get();
        facultyRepository.deleteById(id);
        return faculty;
    }

    public Faculty updateFaculty(Faculty faculty) {
        if (facultyRepository.findById(faculty.getId()).isEmpty()) {
            return null;
        }
        return facultyRepository.save(faculty);
    }

    public Collection<Faculty> facultyByColor(String color) {
        return facultyRepository.findFacultiesByColor(color);
    }

    public Collection<Faculty> facultyByNameOrColor(String name, String color) {
        return facultyRepository.findFacultiesByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    public Collection<Student> getStudentByFaculty(String nameFaculty) {
        Faculty faculty = facultyRepository.findFacultyByNameIgnoreCase(nameFaculty);
        return studentRepository.findStudentsByFaculty_Id(faculty.getId());
    }

}
