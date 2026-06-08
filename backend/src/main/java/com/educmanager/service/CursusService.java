package com.educmanager.service;

import com.educmanager.entity.Cursus;
import com.educmanager.repository.CursusRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursusService {

    private final CursusRepository cursusRepository;

    public CursusService(CursusRepository cursusRepository) {
        this.cursusRepository = cursusRepository;
    }

    public List<Cursus> findAll() {
        return cursusRepository.findAll();
    }

    public Optional<Cursus> findById(Long id) {
        return cursusRepository.findById(id);
    }

    public Cursus create(Cursus cursus) {
        return cursusRepository.save(cursus);
    }

    public void deleteById(Long id) {
        cursusRepository.deleteById(id);
    }
}
