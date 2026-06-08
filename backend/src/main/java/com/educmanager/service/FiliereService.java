package com.educmanager.service;

import com.educmanager.entity.Filiere;
import com.educmanager.repository.FiliereRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FiliereService {

    private final FiliereRepository filiereRepository;

    public FiliereService(FiliereRepository filiereRepository) {
        this.filiereRepository = filiereRepository;
    }

    public List<Filiere> findAll() {
        return filiereRepository.findAll();
    }

    public Optional<Filiere> findById(Long id) {
        return filiereRepository.findById(id);
    }

    public Filiere create(Filiere filiere) {
        return filiereRepository.save(filiere);
    }

    public void deleteById(Long id) {
        filiereRepository.deleteById(id);
    }
}
