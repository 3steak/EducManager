package com.educmanager.service;

import com.educmanager.entity.Filiere;
import com.educmanager.repository.FiliereRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FiliereServiceTest {

    @Mock
    private FiliereRepository filiereRepository;

    @InjectMocks
    private FiliereService filiereService;

    @Test
    void shouldFindAllFilieres() {
        Filiere filiere = Filiere.builder()
                .id(1L)
                .name("Développement")
                .build();
        when(filiereRepository.findAll()).thenReturn(List.of(filiere));

        List<Filiere> filieres = filiereService.findAll();

        assertThat(filieres).containsExactly(filiere);
        verify(filiereRepository).findAll();
    }

    @Test
    void shouldFindFiliereById() {
        Filiere filiere = Filiere.builder()
                .id(1L)
                .name("Développement")
                .build();
        when(filiereRepository.findById(1L)).thenReturn(Optional.of(filiere));

        Filiere result = filiereService.findById(1L);

        assertThat(result).isEqualTo(filiere);
        verify(filiereRepository).findById(1L);
    }

    @Test
    void shouldCreateFiliere() {
        Filiere filiere = Filiere.builder()
                .name("Développement")
                .build();
        Filiere savedFiliere = Filiere.builder()
                .id(1L)
                .name("Développement")
                .build();
        when(filiereRepository.save(filiere)).thenReturn(savedFiliere);

        Filiere result = filiereService.create(filiere);

        assertThat(result).isEqualTo(savedFiliere);
        verify(filiereRepository).save(filiere);
    }

    @Test
    void shouldDeleteFiliereById() {
        Filiere filiere = Filiere.builder()
                .id(1L)
                .name("DÃ©veloppement")
                .build();
        when(filiereRepository.findById(1L)).thenReturn(Optional.of(filiere));

        filiereService.deleteById(1L);

        verify(filiereRepository).findById(1L);
        verify(filiereRepository).delete(filiere);
    }
}
