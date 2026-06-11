package com.educmanager.service;

import com.educmanager.dto.CourseAccessRequest;
import com.educmanager.dto.CursusCourseRequest;
import com.educmanager.entity.Course;
import com.educmanager.entity.Cursus;
import com.educmanager.entity.CursusCourse;
import com.educmanager.repository.CourseRepository;
import com.educmanager.repository.CursusCourseRepository;
import com.educmanager.repository.CursusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CursusCourseService {

    private final CursusCourseRepository cursusCourseRepository;
    private final CursusRepository cursusRepository;
    private final CourseRepository courseRepository;

    public CursusCourseService(
            CursusCourseRepository cursusCourseRepository,
            CursusRepository cursusRepository,
            CourseRepository courseRepository
    ) {
        this.cursusCourseRepository = cursusCourseRepository;
        this.cursusRepository = cursusRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional(readOnly = true)
    public List<CursusCourse> getByCursusId(Long cursusId) {
        ensureCursusExists(cursusId);
        return cursusCourseRepository.findByCursusIdWithDetails(cursusId);
    }

    @Transactional(readOnly = true)
    public CursusCourse getById(Long id) {
        return cursusCourseRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new IllegalArgumentException("Association cursus-cours introuvable : " + id));
    }

    @Transactional
    public CursusCourse create(CursusCourseRequest request) {
        validateRequest(request);

        Cursus cursus = cursusRepository.findById(request.cursusId())
                .orElseThrow(() -> new IllegalArgumentException("Cursus introuvable : " + request.cursusId()));
        Course course = courseRepository.findById(request.courseId())
                .orElseThrow(() -> new IllegalArgumentException("Cours introuvable : " + request.courseId()));

        validateUniqueConstraints(request.cursusId(), request.courseId(), request.ordre(), null);

        CursusCourse cursusCourse = CursusCourse.builder()
                .cursus(cursus)
                .course(course)
                .ordre(request.ordre())
                .build();

        return cursusCourseRepository.save(cursusCourse);
    }

    @Transactional
    public CursusCourse update(Long id, CursusCourseRequest request) {
        validateRequest(request);

        CursusCourse existing = getById(id);

        Cursus cursus = cursusRepository.findById(request.cursusId())
                .orElseThrow(() -> new IllegalArgumentException("Cursus introuvable : " + request.cursusId()));
        Course course = courseRepository.findById(request.courseId())
                .orElseThrow(() -> new IllegalArgumentException("Cours introuvable : " + request.courseId()));

        validateUniqueConstraints(request.cursusId(), request.courseId(), request.ordre(), id);

        existing.setCursus(cursus);
        existing.setCourse(course);
        existing.setOrdre(request.ordre());

        return cursusCourseRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        if (!cursusCourseRepository.existsById(id)) {
            throw new IllegalArgumentException("Association cursus-cours introuvable : " + id);
        }
        cursusCourseRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean canAccessCourse(CourseAccessRequest request) {
        ensureCursusExists(request.cursusId());

        List<CursusCourse> parcours = cursusCourseRepository.findByCursusIdWithDetails(request.cursusId());
        Set<Long> completedCourseIds = new HashSet<>(
                request.completedCourseIds() != null ? request.completedCourseIds() : List.of()
        );

        for (CursusCourse cursusCourse : parcours) {
            Long courseId = cursusCourse.getCourse().getId();
            if (courseId.equals(request.courseId())) {
                return true;
            }
            if (!completedCourseIds.contains(courseId)) {
                return false;
            }
        }

        return false;
    }

    private void ensureCursusExists(Long cursusId) {
        if (!cursusRepository.existsById(cursusId)) {
            throw new IllegalArgumentException("Cursus introuvable : " + cursusId);
        }
    }

    private void validateRequest(CursusCourseRequest request) {
        if (request.cursusId() == null) {
            throw new IllegalArgumentException("L'identifiant du cursus est obligatoire");
        }
        if (request.courseId() == null) {
            throw new IllegalArgumentException("L'identifiant du cours est obligatoire");
        }
        if (request.ordre() == null || request.ordre() < 1) {
            throw new IllegalArgumentException("L'ordre doit être un entier positif");
        }
    }

    private void validateUniqueConstraints(Long cursusId, Long courseId, Integer ordre, Long excludeId) {
        boolean ordreExists = excludeId == null
                ? cursusCourseRepository.existsByCursus_IdAndOrdre(cursusId, ordre)
                : cursusCourseRepository.existsByCursus_IdAndOrdreAndIdNot(cursusId, ordre, excludeId);

        if (ordreExists) {
            throw new IllegalArgumentException("L'ordre " + ordre + " est déjà utilisé dans ce cursus");
        }

        boolean courseExists = excludeId == null
                ? cursusCourseRepository.existsByCursus_IdAndCourse_Id(cursusId, courseId)
                : cursusCourseRepository.existsByCursus_IdAndCourse_IdAndIdNot(cursusId, courseId, excludeId);

        if (courseExists) {
            throw new IllegalArgumentException("Ce cours est déjà associé à ce cursus");
        }
    }
}
