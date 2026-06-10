package com.educmanager.service;

import com.educmanager.entity.Course;
import com.educmanager.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository repository;

    @InjectMocks
    private CourseService service;

    @Test
    void getAllCourses_shouldReturnAllCoursesFromRepository() {
        Course course1 = Course.builder().id(1L).code("JAVA-101").titre("Java").build();
        Course course2 = Course.builder().id(2L).code("ANG-101").titre("Angular").build();
        when(repository.findAll()).thenReturn(List.of(course1, course2));

        List<Course> result = service.getAllCourses();

        assertThat(result).containsExactly(course1, course2);
        verify(repository).findAll();
    }

    @Test
    void createCourse_shouldSaveAndReturnCourse() {
        Course toCreate = Course.builder().code("JAVA-101").titre("Java").dureeJours(5).build();
        Course saved = Course.builder().id(1L).code("JAVA-101").titre("Java").dureeJours(5).build();
        when(repository.save(any(Course.class))).thenReturn(saved);

        Course result = service.createCourse(toCreate);

        assertThat(result).isEqualTo(saved);
        verify(repository).save(toCreate);
    }
}
