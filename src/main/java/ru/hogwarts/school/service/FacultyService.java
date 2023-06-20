package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final Map<Long, Faculty> facultyMap = new HashMap<>();
    private long generateId = 0;

    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(++generateId);
        facultyMap.put(generateId, faculty);
        return faculty;
    }

    public Faculty findFaculty(long id) {
        return facultyMap.get(id);
    }

    public Faculty deleteFaculty(long id) {
        return facultyMap.remove(id);
    }

    public Faculty updateFaculty(Faculty faculty) {
        if (facultyMap.containsKey(faculty.getId())) {
            facultyMap.put(faculty.getId(), faculty);
            return faculty;
        }
        return null;
    }

    public List<Faculty> facultyByColor(String color) {
        return facultyMap.values().stream()
                .filter(f -> f.getColor().equals(color))
                .collect(Collectors.toList());
    }
}
