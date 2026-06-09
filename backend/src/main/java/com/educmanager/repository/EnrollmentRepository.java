package com.educmanager.repository;

import com.educmanager.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudentId(Long studentId);
    boolean existsByStudentIdAndPromotionId(Long studentId, Long promotionId);
    boolean existsByStudentIdAndPromotionCourseScheduleId(Long studentId, Long promotionCourseScheduleId);
}