package com.educmanager.service;

import com.educmanager.entity.Cursus;
import com.educmanager.entity.Filiere;
import com.educmanager.exception.BadRequestException;
import com.educmanager.exception.ResourceNotFoundException;
import com.educmanager.repository.CursusRepository;
import com.educmanager.repository.FiliereRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursusService {

    private final CursusRepository cursusRepository;
    private final FiliereRepository filiereRepository;

    public CursusService(CursusRepository cursusRepository, FiliereRepository filiereRepository) {
        this.cursusRepository = cursusRepository;
        this.filiereRepository = filiereRepository;
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
        Filiere filiere = findFiliere(cursus);
        cursus.setFiliere(filiere);

        return cursusRepository.save(cursus);
    }

    public Cursus update(Long id, Cursus cursus) {
        Cursus existingCursus = cursusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cursus not found"));

        validateName(cursus.getName());
        Filiere filiere = findFiliere(cursus);
        existingCursus.setName(cursus.getName());
        existingCursus.setFiliere(filiere);

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

    private Filiere findFiliere(Cursus cursus) {
        if (cursus.getFiliere() == null || cursus.getFiliere().getId() == null) {
            throw new BadRequestException("Cursus filiere is required");
        }

        return filiereRepository.findById(cursus.getFiliere().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Filiere not found"));
    }
}
