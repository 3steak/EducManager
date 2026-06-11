package com.educmanager.controller;

import com.educmanager.dto.EnrollmentDTO;
import com.educmanager.entity.Enrollment;
import com.educmanager.entity.Student;
import com.educmanager.repository.EnrollmentRepository;
import com.educmanager.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/enrollment")
@CrossOrigin(origins = "http://localhost:4200")
public class EnrollmentController {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping
    @PreAuthorize("hasRole('REFERENTE') or hasRole('ADMIN')")
    public ResponseEntity<?> enrollStudent(@RequestBody EnrollmentDTO dto) {
        // Vérifier si l'élève existe
        Student student = studentRepository.findById(dto.getStudentId()).orElse(null);
        if (student == null) {
            return ResponseEntity.badRequest().body("Élève non trouvé");
        }

        // Vérifier les contraintes métier
        if (dto.getPromotionId() != null) {
            boolean alreadyEnrolled = enrollmentRepository.existsByStudentIdAndPromotionId(
                    dto.getStudentId(),
                    dto.getPromotionId()
            );
            if (alreadyEnrolled && !dto.isForced()) {
                return ResponseEntity.badRequest()
                        .body("Élève déjà inscrit à cette promotion");
            }
        }

        if (dto.getPromotionCourseScheduleId() != null) {
            boolean alreadyEnrolled = enrollmentRepository.existsByStudentIdAndPromotionCourseScheduleId(
                    dto.getStudentId(),
                    dto.getPromotionCourseScheduleId()
            );
            if (alreadyEnrolled && !dto.isForced()) {
                return ResponseEntity.badRequest()
                        .body("Élève déjà inscrit à ce cours");
            }
        }

        // Créer l'inscription
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setPromotionId(dto.getPromotionId());
        enrollment.setPromotionCourseScheduleId(dto.getPromotionCourseScheduleId());
        enrollment.setType(dto.getType());
        enrollment.setEnrollmentDate(LocalDate.now());
        enrollment.setForced(dto.isForced());

        Enrollment saved = enrollmentRepository.save(enrollment);

        EnrollmentDTO response = new EnrollmentDTO(
                saved.getId(),
                saved.getStudent().getId(),
                saved.getPromotionId(),
                saved.getPromotionCourseScheduleId(),
                saved.getType(),
                saved.getEnrollmentDate(),
                saved.isForced()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('REFERENTE') or hasRole('ADMIN')")
    public ResponseEntity<List<EnrollmentDTO>> getStudentEnrollments(@PathVariable Long studentId) {
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);

        List<EnrollmentDTO> dtos = enrollments.stream()
                .map(e -> new EnrollmentDTO(
                        e.getId(),
                        e.getStudent().getId(),
                        e.getPromotionId(),
                        e.getPromotionCourseScheduleId(),
                        e.getType(),
                        e.getEnrollmentDate(),
                        e.isForced()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }
}