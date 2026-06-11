package com.educmanager.dto;

import java.util.List;

public record CourseAccessRequest(Long cursusId, Long courseId, List<Long> completedCourseIds) {

}
