package com.educmanager.service;

import com.educmanager.entity.Filiere;
import com.educmanager.exception.BadRequestException;
import com.educmanager.exception.ResourceNotFoundException;
import com.educmanager.repository.FiliereRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FiliereService {

    private final FiliereRepository filiereRepository;

    public FiliereService(FiliereRepository filiereRepository) {
        this.filiereRepository = filiereRepository;
    }

    public List<Filiere> findAll() {
        return filiereRepository.findAll();
    }

    public Filiere findById(Long id) {
        return filiereRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Filiere not found"));
    }

    public Filiere create(Filiere filiere) {
        validateName(filiere.getName());

        return filiereRepository.save(filiere);
    }

    public Filiere update(Long id, Filiere filiere) {
        Filiere existingFiliere = filiereRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Filiere not found"));

        validateName(filiere.getName());
        existingFiliere.setName(filiere.getName());

        return filiereRepository.save(existingFiliere);
    }

    public void deleteById(Long id) {
        Filiere filiere = filiereRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Filiere not found"));

        filiereRepository.delete(filiere);
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new BadRequestException("Filiere name is required");
        }
    }
}