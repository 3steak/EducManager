package com.educmanager.mapper;

import com.educmanager.dto.FiliereDto;
import com.educmanager.entity.Filiere;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FiliereMapperTest {

    @Test
    void shouldMapFiliereToDto() {
        Filiere filiere = Filiere.builder()
                .id(1L)
                .name("Développement")
                .build();

        FiliereDto dto = FiliereMapper.toDto(filiere);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("Développement");
    }

    @Test
    void shouldMapDtoToFiliere() {
        FiliereDto dto = FiliereDto.builder()
                .id(1L)
                .name("Développement")
                .build();

        Filiere filiere = FiliereMapper.toEntity(dto);

        assertThat(filiere.getId()).isEqualTo(1L);
        assertThat(filiere.getName()).isEqualTo("Développement");
    }
}
