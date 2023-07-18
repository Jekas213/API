package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public Faculty createFaculty(Faculty faculty) {
        logger.info("Was invoked method for create faculty");
        logger.debug("the name of the faculty we are creating: {}", faculty.getName());
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        logger.info("Was invoked method for find faculty");
        logger.debug("id of the faculty we are find: {}", id);
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty deleteFaculty(long id) {
        logger.info("Was invoked method for delete faculty");
        logger.debug("id of the faculty we are deleted: {}", id);
        final Optional<Faculty> optionalFaculty = facultyRepository.findById(id);
        if (optionalFaculty.isEmpty()) {
            return null;
        }
        final Faculty faculty = optionalFaculty.get();
        facultyRepository.deleteById(id);
        return faculty;
    }

    public Faculty updateFaculty(Faculty faculty) {
        logger.info("Was invoked method for update faculty");
        logger.debug("the name of the faculty we are updating: {}", faculty.getName());
        if (facultyRepository.findById(faculty.getId()).isEmpty()) {
            return null;
        }
        return facultyRepository.save(faculty);
    }

    public Collection<Faculty> facultyByColor(String color) {
        logger.info("Was invoked method for find faculty by color");
        logger.debug("color at which we are looking for a faculty: color = {}", color);
        return facultyRepository.findFacultiesByColor(color);
    }

    public Collection<Faculty> facultyByNameOrColor(String name, String color) {
        logger.info("Was invoked method for find faculty by name or color");
        logger.debug("name and color at which we are looking for a faculty: name = {}, color = {}", name, color);
        return facultyRepository.findFacultiesByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    public Collection<Student> getStudentByFaculty(String nameFaculty) {
        logger.info("Was invoked method for get student by faculty");
        logger.debug("the name of the faculty for which we are looking for students: name = {}", nameFaculty);
        Faculty faculty = facultyRepository.findFacultyByNameIgnoreCase(nameFaculty);
        return studentRepository.findStudentsByFaculty_Id(faculty.getId());
    }

}
