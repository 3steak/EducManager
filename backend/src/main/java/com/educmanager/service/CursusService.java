package com.educmanager.service;

import com.educmanager.entity.Cursus;
import com.educmanager.exception.BadRequestException;
import com.educmanager.exception.ResourceNotFoundException;
import com.educmanager.repository.CursusRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursusService {

    private final CursusRepository cursusRepository;

    public CursusService(CursusRepository cursusRepository) {
        this.cursusRepository = cursusRepository;
    }

    public List<Cursus> findAll() {
        return cursusRepository.findAll();
    }

    public Cursus findById(Long id) {
        return cursusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cursus not found"));
    }

    public Cursus create(Cursus cursus) {
        validateName(cursus.getName());
        validateFiliere(cursus);

        return cursusRepository.save(cursus);
    }

    public Cursus update(Long id, Cursus cursus) {
        Cursus existingCursus = cursusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cursus not found"));

        validateName(cursus.getName());
        validateFiliere(cursus);
        existingCursus.setName(cursus.getName());
        existingCursus.setFiliere(cursus.getFiliere());

        return cursusRepository.save(existingCursus);
    }

    public void deleteById(Long id) {
        Cursus cursus = cursusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cursus not found"));

        cursusRepository.delete(cursus);
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new BadRequestException("Cursus name is required");
        }
    }

    private void validateFiliere(Cursus cursus) {
        if (cursus.getFiliere() == null) {
            throw new BadRequestException("Cursus filiere is required");
        }
    }
}