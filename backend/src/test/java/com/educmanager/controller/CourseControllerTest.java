package com.educmanager.controller;

import com.educmanager.entity.Course;
import com.educmanager.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CourseControllerTest {

    @Mock
    private CourseService service;

    @InjectMocks
    private CourseController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getAllCourses_shouldReturn200AndCourseList() throws Exception {
        Course course = Course.builder()
                .id(1L)
                .code("JAVA-101")
                .titre("Java")
                .dureeJours(5)
                .build();
        when(service.getAllCourses()).thenReturn(List.of(course));

        mockMvc.perform(get("/api/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].code").value("JAVA-101"))
                .andExpect(jsonPath("$[0].titre").value("Java"))
                .andExpect(jsonPath("$[0].dureeJours").value(5));
    }

    @Test
    void createCourse_shouldReturnCreatedCourse() throws Exception {
        Course saved = Course.builder()
                .id(1L)
                .code("JAVA-101")
                .titre("Java")
                .dureeJours(5)
                .build();
        when(service.createCourse(any(Course.class))).thenReturn(saved);

        mockMvc.perform(post("/api/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "code": "JAVA-101",
                                  "titre": "Java",
                                  "dureeJours": 5
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.code").value("JAVA-101"));

        verify(service).createCourse(any(Course.class));
    }
}
