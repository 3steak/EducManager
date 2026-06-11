package com.educmanager.repository;

import com.educmanager.entity.Course;
import com.educmanager.entity.Cursus;
import com.educmanager.entity.CursusCourse;
import com.educmanager.entity.Filiere;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CursusCourseRepositoryTest {

    @Autowired
    private CursusCourseRepository cursusCourseRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CursusRepository cursusRepository;

    @Autowired
    private FiliereRepository filiereRepository;

    private Cursus cursus;
    private Course course1;
    private Course course2;

    @BeforeEach
    void setUp() {
        Filiere filiere = filiereRepository.save(Filiere.builder().name("Informatique").build());
        cursus = cursusRepository.save(Cursus.builder().name("Dev Web").filiere(filiere).build());
        course1 = courseRepository.save(Course.builder().code("JAVA-101").titre("Java").build());
        course2 = courseRepository.save(Course.builder().code("ANG-101").titre("Angular").build());
    }

    @Test
    void findByCursusIdWithDetails_shouldReturnAssociationsOrderedByOrdre() {
        cursusCourseRepository.save(CursusCourse.builder().cursus(cursus).course(course2).ordre(2).build());
        cursusCourseRepository.save(CursusCourse.builder().cursus(cursus).course(course1).ordre(1).build());

        List<CursusCourse> result = cursusCourseRepository.findByCursusIdWithDetails(cursus.getId());

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getOrdre()).isEqualTo(1);
        assertThat(result.get(0).getCourse().getCode()).isEqualTo("JAVA-101");
        assertThat(result.get(1).getOrdre()).isEqualTo(2);
        assertThat(result.get(1).getCourse().getCode()).isEqualTo("ANG-101");
    }

    @Test
    void existsByCursus_IdAndOrdre_shouldDetectDuplicateOrdre() {
        cursusCourseRepository.save(CursusCourse.builder().cursus(cursus).course(course1).ordre(1).build());

        assertThat(cursusCourseRepository.existsByCursus_IdAndOrdre(cursus.getId(), 1)).isTrue();
        assertThat(cursusCourseRepository.existsByCursus_IdAndOrdre(cursus.getId(), 2)).isFalse();
    }

    @Test
    void existsByCursus_IdAndCourse_Id_shouldDetectDuplicateCourse() {
        cursusCourseRepository.save(CursusCourse.builder().cursus(cursus).course(course1).ordre(1).build());

        assertThat(cursusCourseRepository.existsByCursus_IdAndCourse_Id(cursus.getId(), course1.getId())).isTrue();
        assertThat(cursusCourseRepository.existsByCursus_IdAndCourse_Id(cursus.getId(), course2.getId())).isFalse();
    }
}
