package org.bakushkin.springregioncatalog.service;

import org.bakushkin.springregioncatalog.controller.dto.NewRegionDto;
import org.bakushkin.springregioncatalog.controller.dto.RegionDto;
import org.bakushkin.springregioncatalog.entity.Region;
import org.bakushkin.springregioncatalog.mapper.RegionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegionServiceTest {

    @Mock
    private RegionMapper regionMapper;

    @InjectMocks
    private RegionService regionService;

    private NewRegionDto newRegionDto;
    private Region region;
    private RegionDto regionDto;

    @BeforeEach
    public void setUp() {
        newRegionDto = new NewRegionDto();
        newRegionDto.setName("Saint-Petersburg");
        newRegionDto.setShortName("SPb");

        region = new Region();
        region.setId(1L);
        region.setName("Saint-Petersburg");
        region.setShortName("SPb");

        regionDto = new RegionDto();
        regionDto.setId(1L);
        regionDto.setName("Saint-Petersburg");
        regionDto.setShortName("SPb");
    }

    @Test
    void addRegion_whenRequestIsValid_shouldReturnRegionDto() {
        // given
        doNothing().when(regionMapper).save(any(Region.class));

        // when
        RegionDto result = regionService.addRegion(newRegionDto);

        // then
        assertNotNull(result);
        assertEquals(result.getName(), newRegionDto.getName());
        assertEquals(result.getShortName(), newRegionDto.getShortName());
        verify(regionMapper).save(any(Region.class));
    }

    @Test
    void getRegion_whenRequestIsValid_shouldReturnRegionDto() {
        // given
        when(regionMapper.findById(anyLong())).thenReturn(region);

        // when
        RegionDto result = regionService.getRegion(1L);

        // then
        assertNotNull(result);
        assertEquals(result.getName(), newRegionDto.getName());
        assertEquals(result.getShortName(), newRegionDto.getShortName());
        verify(regionMapper).findById(1L);
    }

    @Test
    void updateRegion_whenNameAndShortNameUpdated_shouldReturnRegionDto() {
        // given
        when(regionMapper.findById(anyLong())).thenReturn(region);

        // when
        RegionDto result = regionService.updateRegion(1L, regionDto);

        // then
        assertNotNull(result);
        assertEquals("Saint-Petersburg", result.getName());
        assertEquals("SPb", result.getShortName());
        verify(regionMapper).findById(1L);
        verify(regionMapper).update(region);
    }

    @Test
    void updateRegion_whenNameUpdated_shouldReturnRegionDto() {
        // given
        when(regionMapper.findById(anyLong())).thenReturn(region);
        RegionDto partialUpdateDto = new RegionDto();
        partialUpdateDto.setName("Moscow");

        // when
        RegionDto result = regionService.updateRegion(1L, partialUpdateDto);

        // then
        assertNotNull(result);
        assertEquals("Moscow", result.getName());
        assertEquals("SPb", result.getShortName());
        verify(regionMapper).findById(1L);
        verify(regionMapper).update(region);
    }

    @Test
    void deleteRegion_whenRequestIsValid_shouldDeleteRegion() {
        // given
        Long regionId = 1L;

        // when
        regionService.deleteRegion(regionId);

        // then
        verify(regionMapper).delete(regionId);
        verify(regionMapper, times(1)).delete(regionId);
    }

    @Test
    void getAllRegions_whenRequestIsValid_shouldReturnAllRegions() {
        // given
        Region region1 = new Region();
        region1.setId(1L);
        region1.setName("Region1");
        region1.setShortName("R1");

        Region region2 = new Region();
        region2.setId(2L);
        region2.setName("Region2");
        region2.setShortName("R2");

        when(regionMapper.findAll(10, 0)).thenReturn(List.of(region1, region2));

        // when
        List<RegionDto> result = regionService.getAllRegions(10, 0);

        // then
        assertEquals(2, result.size());
        assertEquals("Region1", result.get(0).getName());
        assertEquals("Region2", result.get(1).getName());
        verify(regionMapper).findAll(10, 0);
    }
}