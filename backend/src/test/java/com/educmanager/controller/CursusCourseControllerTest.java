package com.educmanager.controller;

import com.educmanager.dto.CourseAccessRequest;
import com.educmanager.dto.CursusCourseRequest;
import com.educmanager.entity.Course;
import com.educmanager.entity.Cursus;
import com.educmanager.entity.CursusCourse;
import com.educmanager.service.CursusCourseService;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CursusCourseControllerTest {

    @Mock
    private CursusCourseService service;

    @InjectMocks
    private CursusCourseController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getByCursusId_shouldReturn200AndAssociations() throws Exception {
        CursusCourse association = CursusCourse.builder()
                .id(1L)
                .cursus(Cursus.builder().id(1L).name("Dev Web").build())
                .course(Course.builder().id(10L).code("JAVA-101").titre("Java").build())
                .ordre(1)
                .build();
        when(service.getByCursusId(1L)).thenReturn(List.of(association));

        mockMvc.perform(get("/api/cursus-courses").param("cursusId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].ordre").value(1))
                .andExpect(jsonPath("$[0].course.code").value("JAVA-101"));
    }

    @Test
    void create_shouldReturnCreatedAssociation() throws Exception {
        CursusCourse saved = CursusCourse.builder()
                .id(5L)
                .cursus(Cursus.builder().id(1L).name("Dev Web").build())
                .course(Course.builder().id(10L).code("JAVA-101").titre("Java").build())
                .ordre(1)
                .build();
        when(service.create(any(CursusCourseRequest.class))).thenReturn(saved);

        mockMvc.perform(post("/api/cursus-courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "cursusId": 1,
                                  "courseId": 10,
                                  "ordre": 1
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.ordre").value(1));

        verify(service).create(any(CursusCourseRequest.class));
    }

    @Test
    void update_shouldReturnUpdatedAssociation() throws Exception {
        CursusCourse updated = CursusCourse.builder()
                .id(5L)
                .cursus(Cursus.builder().id(1L).name("Dev Web").build())
                .course(Course.builder().id(10L).code("JAVA-101").titre("Java").build())
                .ordre(2)
                .build();
        when(service.update(eq(5L), any(CursusCourseRequest.class))).thenReturn(updated);

        mockMvc.perform(put("/api/cursus-courses/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "cursusId": 1,
                                  "courseId": 10,
                                  "ordre": 2
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ordre").value(2));
    }

    @Test
    void delete_shouldReturn200() throws Exception {
        mockMvc.perform(delete("/api/cursus-courses/5"))
                .andExpect(status().isOk());

        verify(service).delete(5L);
    }

    @Test
    void canAccessCourse_shouldReturnAccessResult() throws Exception {
        when(service.canAccessCourse(any(CourseAccessRequest.class))).thenReturn(true);

        mockMvc.perform(post("/api/cursus-courses/can-access")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "cursusId": 1,
                                  "courseId": 10,
                                  "completedCourseIds": []
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.canAccess").value(true));
    }
}
