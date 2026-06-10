package com.educmanager.service;

import com.educmanager.dto.CourseAccessRequest;
import com.educmanager.dto.CursusCourseRequest;
import com.educmanager.entity.Course;
import com.educmanager.entity.Cursus;
import com.educmanager.entity.CursusCourse;
import com.educmanager.repository.CourseRepository;
import com.educmanager.repository.CursusCourseRepository;
import com.educmanager.repository.CursusRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CursusCourseServiceTest {

    @Mock
    private CursusCourseRepository cursusCourseRepository;

    @Mock
    private CursusRepository cursusRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CursusCourseService service;

    @Test
    void getByCursusId_shouldReturnOrderedAssociations() {
        Long cursusId = 1L;
        List<CursusCourse> associations = List.of(
                buildCursusCourse(1L, cursusId, 10L, 1),
                buildCursusCourse(2L, cursusId, 11L, 2)
        );
        when(cursusRepository.existsById(cursusId)).thenReturn(true);
        when(cursusCourseRepository.findByCursusIdWithDetails(cursusId)).thenReturn(associations);

        List<CursusCourse> result = service.getByCursusId(cursusId);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getOrdre()).isEqualTo(1);
        assertThat(result.get(1).getOrdre()).isEqualTo(2);
    }

    @Test
    void getByCursusId_shouldThrowWhenCursusNotFound() {
        when(cursusRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> service.getByCursusId(99L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Cursus introuvable");
    }

    @Test
    void create_shouldSaveAssociationWhenRequestIsValid() {
        CursusCourseRequest request = new CursusCourseRequest(1L, 10L, 1);
        Cursus cursus = Cursus.builder().id(1L).name("Dev Web").build();
        Course course = Course.builder().id(10L).code("JAVA-101").titre("Java").build();
        CursusCourse saved = buildCursusCourse(5L, 1L, 10L, 1);

        when(cursusRepository.findById(1L)).thenReturn(Optional.of(cursus));
        when(courseRepository.findById(10L)).thenReturn(Optional.of(course));
        when(cursusCourseRepository.existsByCursus_IdAndOrdre(1L, 1)).thenReturn(false);
        when(cursusCourseRepository.existsByCursus_IdAndCourse_Id(1L, 10L)).thenReturn(false);
        when(cursusCourseRepository.save(any(CursusCourse.class))).thenReturn(saved);

        CursusCourse result = service.create(request);

        assertThat(result.getId()).isEqualTo(5L);
        assertThat(result.getOrdre()).isEqualTo(1);
        verify(cursusCourseRepository).save(any(CursusCourse.class));
    }

    @Test
    void create_shouldThrowWhenOrdreIsInvalid() {
        CursusCourseRequest request = new CursusCourseRequest(1L, 10L, 0);

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("ordre");
    }

    @Test
    void create_shouldThrowWhenCursusNotFound() {
        CursusCourseRequest request = new CursusCourseRequest(1L, 10L, 1);
        when(cursusRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Cursus introuvable");
    }

    @Test
    void create_shouldThrowWhenCourseNotFound() {
        CursusCourseRequest request = new CursusCourseRequest(1L, 10L, 1);
        when(cursusRepository.findById(1L)).thenReturn(Optional.of(Cursus.builder().id(1L).build()));
        when(courseRepository.findById(10L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Cours introuvable");
    }

    @Test
    void create_shouldThrowWhenOrdreAlreadyExists() {
        CursusCourseRequest request = new CursusCourseRequest(1L, 10L, 1);
        when(cursusRepository.findById(1L)).thenReturn(Optional.of(Cursus.builder().id(1L).build()));
        when(courseRepository.findById(10L)).thenReturn(Optional.of(Course.builder().id(10L).build()));
        when(cursusCourseRepository.existsByCursus_IdAndOrdre(1L, 1)).thenReturn(true);

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("ordre");
    }

    @Test
    void create_shouldThrowWhenCourseAlreadyLinkedToCursus() {
        CursusCourseRequest request = new CursusCourseRequest(1L, 10L, 1);
        when(cursusRepository.findById(1L)).thenReturn(Optional.of(Cursus.builder().id(1L).build()));
        when(courseRepository.findById(10L)).thenReturn(Optional.of(Course.builder().id(10L).build()));
        when(cursusCourseRepository.existsByCursus_IdAndOrdre(1L, 1)).thenReturn(false);
        when(cursusCourseRepository.existsByCursus_IdAndCourse_Id(1L, 10L)).thenReturn(true);

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("déjà associé");
    }

    @Test
    void delete_shouldRemoveAssociationWhenItExists() {
        when(cursusCourseRepository.existsById(5L)).thenReturn(true);

        service.delete(5L);

        verify(cursusCourseRepository).deleteById(5L);
    }

    @Test
    void delete_shouldThrowWhenAssociationNotFound() {
        when(cursusCourseRepository.existsById(5L)).thenReturn(false);

        assertThatThrownBy(() -> service.delete(5L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("introuvable");
    }

    @Test
    void canAccessCourse_shouldReturnTrueWhenAllPreviousCoursesAreCompleted() {
        Long cursusId = 1L;
        when(cursusRepository.existsById(cursusId)).thenReturn(true);
        when(cursusCourseRepository.findByCursusIdWithDetails(cursusId)).thenReturn(List.of(
                buildCursusCourse(1L, cursusId, 10L, 1),
                buildCursusCourse(2L, cursusId, 11L, 2),
                buildCursusCourse(3L, cursusId, 12L, 3)
        ));

        CourseAccessRequest request = new CourseAccessRequest(cursusId, 12L, List.of(10L, 11L));

        assertThat(service.canAccessCourse(request)).isTrue();
    }

    @Test
    void canAccessCourse_shouldReturnFalseWhenPreviousCourseIsMissing() {
        Long cursusId = 1L;
        when(cursusRepository.existsById(cursusId)).thenReturn(true);
        when(cursusCourseRepository.findByCursusIdWithDetails(cursusId)).thenReturn(List.of(
                buildCursusCourse(1L, cursusId, 10L, 1),
                buildCursusCourse(2L, cursusId, 11L, 2)
        ));

        CourseAccessRequest request = new CourseAccessRequest(cursusId, 11L, List.of());

        assertThat(service.canAccessCourse(request)).isFalse();
    }

    @Test
    void canAccessCourse_shouldReturnFalseWhenCourseIsNotInCursus() {
        Long cursusId = 1L;
        when(cursusRepository.existsById(cursusId)).thenReturn(true);
        when(cursusCourseRepository.findByCursusIdWithDetails(cursusId)).thenReturn(List.of(
                buildCursusCourse(1L, cursusId, 10L, 1)
        ));

        CourseAccessRequest request = new CourseAccessRequest(cursusId, 99L, List.of(10L));

        assertThat(service.canAccessCourse(request)).isFalse();
    }

    @Test
    void canAccessCourse_shouldReturnTrueForFirstCourseInParcours() {
        Long cursusId = 1L;
        when(cursusRepository.existsById(cursusId)).thenReturn(true);
        when(cursusCourseRepository.findByCursusIdWithDetails(cursusId)).thenReturn(List.of(
                buildCursusCourse(1L, cursusId, 10L, 1)
        ));

        CourseAccessRequest request = new CourseAccessRequest(cursusId, 10L, List.of());

        assertThat(service.canAccessCourse(request)).isTrue();
        verify(cursusCourseRepository).findByCursusIdWithDetails(eq(cursusId));
    }

    private CursusCourse buildCursusCourse(Long id, Long cursusId, Long courseId, int ordre) {
        return CursusCourse.builder()
                .id(id)
                .cursus(Cursus.builder().id(cursusId).name("Cursus").build())
                .course(Course.builder().id(courseId).code("CODE").titre("Titre").build())
                .ordre(ordre)
                .build();
    }
}
