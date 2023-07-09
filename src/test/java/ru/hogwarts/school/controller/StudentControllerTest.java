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
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private FacultyRepository facultyRepository;
    @SpyBean
    private StudentService studentService;
    @InjectMocks
    private StudentController studentController;

    @Test
    void find() throws Exception {
        final long id = 1;
        final String name = "a";
        final int age = 1;

        Student student = new Student(id, name, age);

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

        mockMvc.perform(get("/student/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    void getStudent() throws Exception {
        final int age = 1;

        Student student = new Student(1, "a", age);
        Student student1 = new Student(2, "b", age);

        Collection<Student> students = List.of(student, student1);

        when(studentRepository.findStudentsByAge(anyInt())).thenReturn(students);

        mockMvc.perform(get("/student/age/" + age)
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
    void getStudentByAge() throws Exception {
        Student student = new Student(1, "a", 2);
        Student student1 = new Student(2, "b", 2);

        Collection<Student> students = List.of(student, student1);

        when(studentRepository.findStudentsByAgeBetween(anyInt(), anyInt())).thenReturn(students);

        mockMvc.perform(get("/student/age/")
                        .param("minAge", "1")
                        .param("maxAge", "3")
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
    void getFacultyByStudent() throws Exception {
        final int age = 1;
        Faculty faculty = new Faculty(1, "faculty", "blue");
        Student student = new Student(1, "a", age);

        student.setFaculty(faculty);

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        when(facultyRepository.findById(anyLong())).thenReturn(Optional.of(faculty));

        mockMvc.perform(get("/student/faculty/" + age))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));
    }

    @Test
    void create() throws Exception {
        final long id = 1;
        final String name = "a";
        final int age = 1;

        JSONObject studentObject = new JSONObject();

        studentObject.put("name", name);
        studentObject.put("age", age);

        Student student = new Student(id, name, age);

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));

    }

    @Test
    void update() throws Exception {
        final long id = 1;
        final String name = "a";
        final int age = 1;

        JSONObject studentObject = new JSONObject();

        studentObject.put("name", name);
        studentObject.put("age", age);

        Student student = new Student(id, name, age);

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(put("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));

    }

    @Test
    void deleteTest() throws Exception {
        final long id = 1;
        final String name = "a";
        final int age = 1;

        Student student = new Student(id, name, age);

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

        mockMvc.perform(delete(("/student/" + id))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));

    }
}