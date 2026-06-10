package com.educmanager.service;

import com.educmanager.entity.Cursus;
import com.educmanager.entity.Filiere;
import com.educmanager.exception.BadRequestException;
import com.educmanager.exception.ResourceNotFoundException;
import com.educmanager.repository.CursusRepository;
import com.educmanager.repository.FiliereRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CursusServiceTest {

    @Mock
    private CursusRepository cursusRepository;

    @Mock
    private FiliereRepository filiereRepository;

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

        Cursus result = cursusService.findById(1L);

        assertThat(result).isEqualTo(cursus);
        verify(cursusRepository).findById(1L);
    }

    @Test
    void shouldThrowWhenCursusDoesNotExist() {
        when(cursusRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cursusService.findById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Cursus not found");

        verify(cursusRepository).findById(1L);
    }

    @Test
    void shouldCreateCursusWithExistingFiliere() {
        Filiere filiere = Filiere.builder()
                .id(1L)
                .name("Développement")
                .build();
        Cursus cursus = Cursus.builder()
                .name("CDA")
                .filiere(Filiere.builder().id(1L).build())
                .build();
        Cursus savedCursus = Cursus.builder()
                .id(1L)
                .name("CDA")
                .filiere(filiere)
                .build();
        when(filiereRepository.findById(1L)).thenReturn(Optional.of(filiere));
        when(cursusRepository.save(cursus)).thenReturn(savedCursus);

        Cursus result = cursusService.create(cursus);

        assertThat(result).isEqualTo(savedCursus);
        assertThat(cursus.getFiliere()).isEqualTo(filiere);
        verify(filiereRepository).findById(1L);
        verify(cursusRepository).save(cursus);
    }

    @Test
    void shouldThrowWhenCreatingCursusWithoutFiliere() {
        Cursus cursus = Cursus.builder()
                .name("CDA")
                .build();

        assertThatThrownBy(() -> cursusService.create(cursus))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Cursus filiere is required");

        verify(filiereRepository, never()).findById(any(Long.class));
        verify(cursusRepository, never()).save(any(Cursus.class));
    }

    @Test
    void shouldThrowWhenCreatingCursusWithUnknownFiliere() {
        Cursus cursus = Cursus.builder()
                .name("CDA")
                .filiere(Filiere.builder().id(1L).build())
                .build();
        when(filiereRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cursusService.create(cursus))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Filiere not found");

        verify(filiereRepository).findById(1L);
        verify(cursusRepository, never()).save(any(Cursus.class));
    }

    @Test
    void shouldUpdateCursusWithExistingFiliere() {
        Filiere filiere = Filiere.builder()
                .id(1L)
                .name("Développement")
                .build();
        Cursus existingCursus = Cursus.builder()
                .id(1L)
                .name("Ancien nom")
                .filiere(filiere)
                .build();
        Cursus update = Cursus.builder()
                .name("CDA")
                .filiere(Filiere.builder().id(1L).build())
                .build();
        Cursus savedCursus = Cursus.builder()
                .id(1L)
                .name("CDA")
                .filiere(filiere)
                .build();
        when(cursusRepository.findById(1L)).thenReturn(Optional.of(existingCursus));
        when(filiereRepository.findById(1L)).thenReturn(Optional.of(filiere));
        when(cursusRepository.save(existingCursus)).thenReturn(savedCursus);

        Cursus result = cursusService.update(1L, update);

        assertThat(result).isEqualTo(savedCursus);
        assertThat(existingCursus.getName()).isEqualTo("CDA");
        assertThat(existingCursus.getFiliere()).isEqualTo(filiere);
        verify(cursusRepository).findById(1L);
        verify(filiereRepository).findById(1L);
        verify(cursusRepository).save(existingCursus);
    }

    @Test
    void shouldThrowWhenUpdatingCursusWithUnknownFiliere() {
        Cursus existingCursus = Cursus.builder()
                .id(1L)
                .name("Ancien nom")
                .build();
        Cursus update = Cursus.builder()
                .name("CDA")
                .filiere(Filiere.builder().id(99L).build())
                .build();
        when(cursusRepository.findById(1L)).thenReturn(Optional.of(existingCursus));
        when(filiereRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cursusService.update(1L, update))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Filiere not found");

        verify(cursusRepository).findById(1L);
        verify(filiereRepository).findById(99L);
        verify(cursusRepository, never()).save(any(Cursus.class));
    }

    @Test
    void shouldDeleteCursusById() {
        Cursus cursus = Cursus.builder()
                .id(1L)
                .name("CDA")
                .build();
        when(cursusRepository.findById(1L)).thenReturn(Optional.of(cursus));

        cursusService.deleteById(1L);

        verify(cursusRepository).findById(1L);
        verify(cursusRepository).delete(cursus);
    }

    @Test
    void shouldThrowWhenDeletingUnknownCursus() {
        when(cursusRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cursusService.deleteById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Cursus not found");

        verify(cursusRepository).findById(1L);
        verify(cursusRepository, never()).delete(any(Cursus.class));
    }
}
