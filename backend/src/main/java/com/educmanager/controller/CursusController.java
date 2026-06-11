package com.educmanager.controller;

import com.educmanager.dto.CursusDto;
import com.educmanager.entity.Cursus;
import com.educmanager.mapper.CursusMapper;
import com.educmanager.service.CursusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cursus")
public class CursusController {

    private final CursusService cursusService;

    public CursusController(CursusService cursusService) {
        this.cursusService = cursusService;
    }

    @GetMapping
    public List<CursusDto> findAll() {
        return cursusService.findAll()
                .stream()
                .map(CursusMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursusDto> findById(@PathVariable Long id) {
        Cursus cursus = cursusService.findById(id);

        return ResponseEntity.ok(CursusMapper.toDto(cursus));
    }

    @PostMapping
    public ResponseEntity<CursusDto> create(@RequestBody CursusDto dto) {
        Cursus cursus = CursusMapper.toEntity(dto);
        Cursus savedCursus = cursusService.create(cursus);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CursusMapper.toDto(savedCursus));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursusDto> update(@PathVariable Long id, @RequestBody CursusDto dto) {
        Cursus cursus = CursusMapper.toEntity(dto);
        Cursus updatedCursus = cursusService.update(id, cursus);

        return ResponseEntity.ok(CursusMapper.toDto(updatedCursus));
    }

    @PostMapping("/{id}/courses")
    public ResponseEntity<CursusDto> addCourse(@PathVariable Long id) {
        Cursus cursus = cursusService.findById(id);

        return ResponseEntity.ok(CursusMapper.toDto(cursus));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        cursusService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
