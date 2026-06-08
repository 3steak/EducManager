package com.educmanager.controller;

import com.educmanager.dto.CursusDto;
import com.educmanager.entity.Cursus;
import com.educmanager.entity.Filiere;
import com.educmanager.exception.ResourceNotFoundException;
import com.educmanager.service.CursusService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CursusController.class)
@AutoConfigureMockMvc(addFilters = false)
class CursusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CursusService cursusService;

    @Test
    void shouldFindAllCursus() throws Exception {
        Filiere filiere = Filiere.builder()
                .id(1L)
                .name("Développement")
                .build();
        Cursus cursus = Cursus.builder()
                .id(2L)
                .name("CDA")
                .filiere(filiere)
                .build();
        when(cursusService.findAll()).thenReturn(List.of(cursus));

        mockMvc.perform(get("/api/cursus"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("CDA"));
    }

    @Test
    void shouldFindCursusById() throws Exception {
        Filiere filiere = Filiere.builder()
                .id(1L)
                .name("Développement")
                .build();
        Cursus cursus = Cursus.builder()
                .id(2L)
                .name("CDA")
                .filiere(filiere)
                .build();
        when(cursusService.findById(2L)).thenReturn(cursus);

        mockMvc.perform(get("/api/cursus/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("CDA"));
    }

    @Test
    void shouldReturnNotFoundWhenCursusDoesNotExist() throws Exception {
        when(cursusService.findById(2L)).thenThrow(new ResourceNotFoundException("Cursus not found"));

        mockMvc.perform(get("/api/cursus/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateCursus() throws Exception {
        CursusDto dto = CursusDto.builder()
                .name("CDA")
                .filiereId(1L)
                .build();
        Filiere filiere = Filiere.builder()
                .id(1L)
                .name("Développement")
                .build();
        Cursus savedCursus = Cursus.builder()
                .id(2L)
                .name("CDA")
                .filiere(filiere)
                .build();
        when(cursusService.create(any(Cursus.class))).thenReturn(savedCursus);

        mockMvc.perform(post("/api/cursus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2L));
    }

    @Test
    void shouldDeleteCursus() throws Exception {
        mockMvc.perform(delete("/api/cursus/2"))
                .andExpect(status().isNoContent());

        verify(cursusService).deleteById(2L);
    }
}
