package com.educmanager.repository;

import com.educmanager.entity.Cursus;
import com.educmanager.entity.Filiere;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CursusRepositoryTest {

    @Autowired
    private CursusRepository cursusRepository;

    @Autowired
    private FiliereRepository filiereRepository;

    @Test
    void shouldSaveCursusLinkedToFiliere() {
        Filiere filiere = Filiere.builder()
                .name("Développement")
                .build();
        Filiere savedFiliere = filiereRepository.save(filiere);

        Cursus cursus = Cursus.builder()
                .name("CDA")
                .filiere(savedFiliere)
                .build();

        Cursus savedCursus = cursusRepository.save(cursus);

        assertThat(savedCursus.getId()).isNotNull();
        assertThat(savedCursus.getFiliere()).isEqualTo(savedFiliere);
        assertThat(savedCursus.getName()).isEqualTo("CDA");
    }
}
