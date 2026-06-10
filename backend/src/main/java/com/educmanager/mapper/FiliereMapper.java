package com.educmanager.mapper;

import com.educmanager.dto.FiliereDto;
import com.educmanager.entity.Filiere;

public final class FiliereMapper {

    private FiliereMapper() {
    }

    public static FiliereDto toDto(Filiere filiere) {
        if (filiere == null) {
            return null;
        }

        return FiliereDto.builder()
                .id(filiere.getId())
                .name(filiere.getName())
                .build();
    }

    public static Filiere toEntity(FiliereDto dto) {
        if (dto == null) {
            return null;
        }

        return Filiere.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }
}