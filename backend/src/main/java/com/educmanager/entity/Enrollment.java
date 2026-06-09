package com.educmanager.entity;

import com.educmanager.enums.EnrollmentType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(
        name = "enrollment",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"student_id", "promotion_id"}),
                @UniqueConstraint(columnNames = {"student_id", "promotion_course_schedule_id"})
        }
)
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean forced;

    @Column(nullable = false)
    private LocalDate enrollmentDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnrollmentType type;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(name = "promotion_id")
    private Long promotionId;

    @Column(name = "promotion_course_schedule_id")
    private Long promotionCourseScheduleId;
}