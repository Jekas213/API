package ru.hogwarts.school.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class FacultyControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private FacultyRepository facultyRepository;
    @SpyBean
    private FacultyService facultyService;
    @InjectMocks
    private FacultyController facultyController;

    @Test
    void find() throws Exception {
        final long id = 1;
        final String name = "a";
        final String color = "blue";

        Faculty faculty = new Faculty(id, name, color);

        when(facultyRepository.findById(anyLong())).thenReturn(Optional.of(faculty));

        mockMvc.perform(get("/faculty/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    void getFaculty() throws Exception {
        final String color = "blue";
        Faculty faculty = new Faculty(1, "a", color);
        Faculty faculty1 = new Faculty(2, "b", color);

        Collection<Faculty> faculties = List.of(faculty, faculty1);

        when(facultyRepository.findFacultiesByColor(anyString())).thenReturn(faculties);

        mockMvc.perform(get("/faculty/color/" + color)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id").value(faculty.getId()))
                .andExpect(jsonPath("$.[0].name").value(faculty.getName()))
                .andExpect(jsonPath("$.[0].color").value(faculty.getColor()))
                .andExpect(jsonPath("$.[1].id").value(faculty1.getId()))
                .andExpect(jsonPath("$.[1].name").value(faculty1.getName()))
                .andExpect(jsonPath("$.[1].color").value(faculty1.getColor()));
    }

    @Test
    void getFacultyByNameOrColor() throws Exception {
        final String name = "a";
        final String color = "blue";

        Faculty faculty = new Faculty(1, name, "green");
        Faculty faculty1 = new Faculty(2, "b", color);

        Collection<Faculty> faculties = List.of(faculty, faculty1);

        when(facultyRepository.findFacultiesByNameIgnoreCaseOrColorIgnoreCase(anyString(), anyString())).thenReturn(faculties);

        mockMvc.perform(get("/faculty")
                        .param("name", "name")
                        .param("color", "color")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id").value(faculty.getId()))
                .andExpect(jsonPath("$.[0].name").value(faculty.getName()))
                .andExpect(jsonPath("$.[0].color").value(faculty.getColor()))
                .andExpect(jsonPath("$.[1].id").value(faculty1.getId()))
                .andExpect(jsonPath("$.[1].name").value(faculty1.getName()))
                .andExpect(jsonPath("$.[1].color").value(faculty1.getColor()));
    }

    @Test
    void getStudents() throws Exception {
        final String name = "a";
        Student student = new Student(1, "a", 2);
        Student student1 = new Student(2, "b", 3);

        Faculty faculty = new Faculty(1, name, "blue");

        Collection<Student> students = List.of(student, student1);

        when(facultyRepository.findFacultyByNameIgnoreCase(anyString())).thenReturn(faculty);
        when(studentRepository.findStudentsByFaculty_Id(anyLong())).thenReturn(students);

        mockMvc.perform(get("/faculty/" + name + "/students")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id").value(student.getId()))
                .andExpect(jsonPath("$.[0].name").value(student.getName()))
                .andExpect(jsonPath("$.[0].age").value(student.getAge()))
                .andExpect(jsonPath("$.[1].id").value(student1.getId()))
                .andExpect(jsonPath("$.[1].name").value(student1.getName()))
                .andExpect(jsonPath("$.[1].age").value(student1.getAge()));
    }

    @Test
    void create() throws Exception {
        final long id = 1;
        final String name = "a";
        final String color = "blue";

        JSONObject facultyObject = new JSONObject();

        facultyObject.put("name", name);
        facultyObject.put("color", color);

        Faculty faculty = new Faculty(id, name, color);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    void update() throws Exception {
        final long id = 1;
        final String name = "a";
        final String color = "blue";

        JSONObject facultyObject = new JSONObject();

        facultyObject.put("name", name);
        facultyObject.put("color", color);

        Faculty faculty = new Faculty(id, name, color);

        when(facultyRepository.findById(anyLong())).thenReturn(Optional.of(faculty));
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(put("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    void deleteTest() throws Exception {
        final long id = 1;
        final String name = "a";
        final String color = "blue";

        Faculty faculty = new Faculty(id, name, color);

        when(facultyRepository.findById(anyLong())).thenReturn(Optional.of(faculty));

        mockMvc.perform(delete("/faculty/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }
}