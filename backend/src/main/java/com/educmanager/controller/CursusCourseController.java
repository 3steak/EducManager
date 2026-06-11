package com.educmanager.controller;

import com.educmanager.dto.CourseAccessRequest;
import com.educmanager.dto.CursusCourseRequest;
import com.educmanager.entity.CursusCourse;
import com.educmanager.service.CursusCourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cursus-courses")
public class CursusCourseController {

    private final CursusCourseService service;

    public CursusCourseController(CursusCourseService service) {
        this.service = service;
    }

    @GetMapping
    public List<CursusCourse> getByCursusId(@RequestParam Long cursusId) {
        return service.getByCursusId(cursusId);
    }

    @GetMapping("/{id}")
    public CursusCourse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public CursusCourse create(@RequestBody CursusCourseRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public CursusCourse update(@PathVariable Long id, @RequestBody CursusCourseRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PostMapping("/can-access")
    public Map<String, Boolean> canAccessCourse(@RequestBody CourseAccessRequest request) {
        return Map.of("canAccess", service.canAccessCourse(request));
    }
}
