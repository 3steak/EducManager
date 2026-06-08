package com.educmanager.controller;

import com.educmanager.dto.FiliereDto;
import com.educmanager.entity.Filiere;
import com.educmanager.mapper.FiliereMapper;
import com.educmanager.service.FiliereService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/filieres")
public class FiliereController {

    private final FiliereService filiereService;

    public FiliereController(FiliereService filiereService) {
        this.filiereService = filiereService;
    }

    @GetMapping
    public List<FiliereDto> findAll() {
        return filiereService.findAll()
                .stream()
                .map(FiliereMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FiliereDto> findById(@PathVariable Long id) {
        Filiere filiere = filiereService.findById(id);

        return ResponseEntity.ok(FiliereMapper.toDto(filiere));
    }

    @PostMapping
    public ResponseEntity<FiliereDto> create(@RequestBody FiliereDto dto) {
        Filiere filiere = FiliereMapper.toEntity(dto);
        Filiere savedFiliere = filiereService.create(filiere);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(FiliereMapper.toDto(savedFiliere));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        filiereService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
