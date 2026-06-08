package com.educmanager.service;

import com.educmanager.entity.Cursus;
import com.educmanager.entity.Filiere;
import com.educmanager.repository.CursusRepository;
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
class CursusServiceTest {

    @Mock
    private CursusRepository cursusRepository;

    @InjectMocks
    private CursusService cursusService;

    @Test
    void shouldFindAllCursus() {
        Cursus cursus = Cursus.builder()
                .id(1L)
                .name("CDA")
                .build();
        when(cursusRepository.findAll()).thenReturn(List.of(cursus));

        List<Cursus> cursusList = cursusService.findAll();

        assertThat(cursusList).containsExactly(cursus);
        verify(cursusRepository).findAll();
    }

    @Test
    void shouldFindCursusById() {
        Cursus cursus = Cursus.builder()
                .id(1L)
                .name("CDA")
                .build();
        when(cursusRepository.findById(1L)).thenReturn(Optional.of(cursus));

        Optional<Cursus> result = cursusService.findById(1L);

        assertThat(result).contains(cursus);
        verify(cursusRepository).findById(1L);
    }

    @Test
    void shouldCreateCursus() {
        Filiere filiere = Filiere.builder()
                .id(1L)
                .name("Développement")
                .build();
        Cursus cursus = Cursus.builder()
                .name("CDA")
                .filiere(filiere)
                .build();
        Cursus savedCursus = Cursus.builder()
                .id(1L)
                .name("CDA")
                .filiere(filiere)
                .build();
        when(cursusRepository.save(cursus)).thenReturn(savedCursus);

        Cursus result = cursusService.create(cursus);

        assertThat(result).isEqualTo(savedCursus);
        verify(cursusRepository).save(cursus);
    }

    @Test
    void shouldDeleteCursusById() {
        cursusService.deleteById(1L);

        verify(cursusRepository).deleteById(1L);
    }
}
