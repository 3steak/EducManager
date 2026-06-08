package com.educmanager.mapper;

import com.educmanager.dto.CursusDto;
import com.educmanager.entity.Cursus;
import com.educmanager.entity.Filiere;

public final class CursusMapper {

    private CursusMapper() {
    }

    public static CursusDto toDto(Cursus cursus) {
        if (cursus == null) {
            return null;
        }

        Filiere filiere = cursus.getFiliere();

        return CursusDto.builder()
                .id(cursus.getId())
                .name(cursus.getName())
                .filiereId(filiere != null ? filiere.getId() : null)
                .filiereName(filiere != null ? filiere.getName() : null)
                .build();
    }

    public static Cursus toEntity(CursusDto dto) {
        if (dto == null) {
            return null;
        }

        Filiere filiere = null;
        if (dto.getFiliereId() != null) {
            filiere = Filiere.builder()
                    .id(dto.getFiliereId())
                    .build();
        }

        return Cursus.builder()
                .id(dto.getId())
                .name(dto.getName())
                .filiere(filiere)
                .build();
    }
}
