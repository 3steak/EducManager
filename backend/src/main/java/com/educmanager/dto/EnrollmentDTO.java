package com.educmanager.dto;

import com.educmanager.enums.EnrollmentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentDTO {
    private Long id;
    private Long studentId;
    private Long promotionId;
    private Long promotionCourseScheduleId;
    private EnrollmentType type;
    private LocalDate enrollmentDate;
    private boolean forced;
}