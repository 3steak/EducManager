package com.educmanager.controller;

import com.educmanager.dto.FiliereDto;
import com.educmanager.entity.Filiere;
import com.educmanager.service.FiliereService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FiliereController.class)
@AutoConfigureMockMvc(addFilters = false)
class FiliereControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private FiliereService filiereService;

    @Test
    void shouldFindAllFilieres() throws Exception {
        Filiere filiere = Filiere.builder()
                .id(1L)
                .name("Développement")
                .build();
        when(filiereService.findAll()).thenReturn(List.of(filiere));

        mockMvc.perform(get("/api/filieres"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Développement"));
    }

    @Test
    void shouldFindFiliereById() throws Exception {
        Filiere filiere = Filiere.builder()
                .id(1L)
                .name("Développement")
                .build();
        when(filiereService.findById(1L)).thenReturn(Optional.of(filiere));

        mockMvc.perform(get("/api/filieres/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Développement"));
    }

    @Test
    void shouldReturnNotFoundWhenFiliereDoesNotExist() throws Exception {
        when(filiereService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/filieres/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateFiliere() throws Exception {
        FiliereDto dto = FiliereDto.builder()
                .name("Développement")
                .build();
        Filiere savedFiliere = Filiere.builder()
                .id(1L)
                .name("Développement")
                .build();
        when(filiereService.create(any(Filiere.class))).thenReturn(savedFiliere);

        mockMvc.perform(post("/api/filieres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void shouldDeleteFiliere() throws Exception {
        mockMvc.perform(delete("/api/filieres/1"))
                .andExpect(status().isNoContent());

        verify(filiereService).deleteById(1L);
    }
}
