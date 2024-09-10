package org.bakushkin.springregioncatalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.bakushkin.springregioncatalog.dto.NewRegionDto;
import org.bakushkin.springregioncatalog.dto.RegionDto;
import org.bakushkin.springregioncatalog.dto.UpdateRegionDto;
import org.bakushkin.springregioncatalog.exception.NotFoundException;
import org.bakushkin.springregioncatalog.service.RegionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(RegionController.class)
class RegionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RegionService regionService;

    @Test
    @SneakyThrows
    void addRegion_whenRequestIsValid_shouldAddRegion() {
        // given
        NewRegionDto newRegionDto = new NewRegionDto();
        newRegionDto.setName("Saint-Petersburg");
        newRegionDto.setShortName("SPb");

        RegionDto regionDto = createRegionDto();

        when(regionService.addRegion(newRegionDto)).thenReturn(regionDto);

        // when & then
        mockMvc.perform(post("/regions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newRegionDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Saint-Petersburg"))
                .andExpect(jsonPath("$.shortName").value("SPb"));
    }

    @Test
    @SneakyThrows
    void addRegion_whenNameOrShortNameIsNull_shouldThrowsMethodArgumentNotValidException() {
        // given
        NewRegionDto newRegionDto = new NewRegionDto();
        newRegionDto.setName("Saint-Petersburg");

        // when & then
        mockMvc.perform(post("/regions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newRegionDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.reason").value("Incorrect request. " +
                        "Field: \"shortName\" Reason: \"must not be blank\""))
                .andExpect(jsonPath("$.status").value("400 BAD_REQUEST"));

        // & given
        newRegionDto.setName(null);
        newRegionDto.setShortName("SPb");
        // when & then
        mockMvc.perform(post("/regions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newRegionDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.reason").value("Incorrect request. " +
                        "Field: \"name\" Reason: \"must not be blank\""))
                .andExpect(jsonPath("$.status").value("400 BAD_REQUEST"));
    }

    @Test
    @SneakyThrows
    void getRegion_whenRequestIsValid_shouldReturnRegion() {
        // given
        RegionDto regionDto = createRegionDto();

        when(regionService.getRegion(1L)).thenReturn(regionDto);

        // when & then
        mockMvc.perform(get("/regions/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Saint-Petersburg"))
                .andExpect(jsonPath("$.shortName").value("SPb"));
    }

    @Test
    @SneakyThrows
    void getRegion_whenRegionIsNotFound_shouldThrowsNotFoundException() {
        //given
        when(regionService.getRegion(1L)).thenThrow(new NotFoundException("Region with id 1 not found"));

        // when & then
        mockMvc.perform(get("/regions/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Region with id 1 not found"))
                .andExpect(jsonPath("$.status").value("404 NOT_FOUND"));
    }

    @Test
    @SneakyThrows
    void updateRegion_whenRequestIsValid_shouldUpdateRegion() {
        // given
        RegionDto regionDto = createRegionDto();
        regionDto.setName("Moscow");

        UpdateRegionDto updateRegionDto = new UpdateRegionDto();
        updateRegionDto.setName("Moscow");

        when(regionService.updateRegion(1L, updateRegionDto)).thenReturn(regionDto);

        // when & then
        mockMvc.perform(patch("/regions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRegionDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Moscow"))
                .andExpect(jsonPath("$.shortName").value("SPb"));
    }

    @Test
    @SneakyThrows
    void deleteRegion_whenRequestIsValid_shouldDeleteRegion() {
        // given
        doNothing().when(regionService).deleteRegion(1L);

        // then
        mockMvc.perform(
                        delete("/regions/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @SneakyThrows
    void getAllRegions_whenRequestIsValid_shouldReturnOneRegion() {
        // given
        RegionDto regionDto1 = createRegionDto();

        when(regionService.getAllRegions(1, 0)).thenReturn(List.of(regionDto1));

        // when & then
        mockMvc.perform(
                        get("/regions")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("limit", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNotEmpty())
                .andExpect(jsonPath("$[0].name").value("Saint-Petersburg"))
                .andExpect(jsonPath("$[0].shortName").value("SPb"));
    }

    private RegionDto createRegionDto() {
        RegionDto regionDto = new RegionDto();
        regionDto.setId(1L);
        regionDto.setName("Saint-Petersburg");
        regionDto.setShortName("SPb");
        return regionDto;
    }
}