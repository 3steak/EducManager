package com.educmanager.repository;

import com.educmanager.entity.Filiere;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class FiliereRepositoryTest {

    @Autowired
    private FiliereRepository filiereRepository;

    @Test
    void shouldSaveFiliere() {
        Filiere filiere = Filiere.builder()
                .name("Développement")
                .build();

        Filiere savedFiliere = filiereRepository.save(filiere);

        assertThat(savedFiliere.getId()).isNotNull();
        assertThat(savedFiliere.getName()).isEqualTo("Développement");
    }
}
