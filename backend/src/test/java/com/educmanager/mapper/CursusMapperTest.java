package com.educmanager.mapper;

import com.educmanager.dto.CursusDto;
import com.educmanager.entity.Cursus;
import com.educmanager.entity.Filiere;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CursusMapperTest {

    @Test
    void shouldMapCursusToDto() {
        Filiere filiere = Filiere.builder()
                .id(1L)
                .name("Développement")
                .build();
        Cursus cursus = Cursus.builder()
                .id(2L)
                .name("CDA")
                .filiere(filiere)
                .build();

        CursusDto dto = CursusMapper.toDto(cursus);

        assertThat(dto.getId()).isEqualTo(2L);
        assertThat(dto.getName()).isEqualTo("CDA");
        assertThat(dto.getFiliereId()).isEqualTo(1L);
        assertThat(dto.getFiliereName()).isEqualTo("Développement");
    }

    @Test
    void shouldMapDtoToCursus() {
        CursusDto dto = CursusDto.builder()
                .id(2L)
                .name("CDA")
                .filiereId(1L)
                .filiereName("Développement")
                .build();

        Cursus cursus = CursusMapper.toEntity(dto);

        assertThat(cursus.getId()).isEqualTo(2L);
        assertThat(cursus.getName()).isEqualTo("CDA");
        assertThat(cursus.getFiliere()).isNotNull();
        assertThat(cursus.getFiliere().getId()).isEqualTo(1L);
        assertThat(cursus.getFiliere().getName()).isNull();
    }
}
