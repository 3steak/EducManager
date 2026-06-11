package com.educmanager.repository;

import com.educmanager.entity.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CourseRepositoryTest {

    @Autowired
    private CourseRepository repository;

    @Test
    void save_shouldPersistCourse() {
        Course course = Course.builder()
                .code("JAVA-101")
                .titre("Introduction à Java")
                .description("Les bases")
                .dureeJours(5)
                .build();

        Course saved = repository.save(course);

        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findById(saved.getId()))
                .isPresent()
                .get()
                .satisfies(found -> {
                    assertThat(found.getCode()).isEqualTo("JAVA-101");
                    assertThat(found.getTitre()).isEqualTo("Introduction à Java");
                    assertThat(found.getDureeJours()).isEqualTo(5);
                });
    }

    @Test
    void findAll_shouldReturnSavedCourses() {
        repository.save(Course.builder().code("JAVA-101").titre("Java").build());
        repository.save(Course.builder().code("ANG-101").titre("Angular").build());

        assertThat(repository.findAll()).hasSize(2);
    }
}
