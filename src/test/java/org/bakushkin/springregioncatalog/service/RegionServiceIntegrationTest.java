package org.bakushkin.springregioncatalog.service;

import lombok.RequiredArgsConstructor;
import org.bakushkin.springregioncatalog.controller.dto.RegionDto;
import org.bakushkin.springregioncatalog.entity.Region;
import org.bakushkin.springregioncatalog.mapper.RegionMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
class RegionServiceIntegrationTest {

    private final RegionService regionService;

    private final RegionMapper regionMapper;

    @Test
    public void getRegion() {
        // given
        Region newRegion = new Region();
        newRegion.setName("Saint-Petersburg");
        newRegion.setShortName("SBp");
        regionMapper.save(newRegion);

        // when
        RegionDto regionDto = regionService.getRegion(newRegion.getId());

        // then
        assertNotNull(regionDto);
        assertEquals("Saint-Petersburg", regionDto.getName());
        assertEquals("SBp", regionDto.getShortName());
        assertEquals(newRegion.getId(), regionDto.getId());
    }

    @Test
    public void getAllRegions() {
        // given
        Region region1 = new Region();
        region1.setName("Saint-Petersburg");
        region1.setShortName("SBp");
        regionMapper.save(region1);

        Region region2 = new Region();
        region2.setName("Moscow");
        region2.setShortName("MSK");
        regionMapper.save(region2);

        // when
        List<RegionDto> allRegions = regionService.getAllRegions(1, 0);

        // then
        assertNotNull(allRegions);
        assertEquals(1, allRegions.size());
        assertEquals("Saint-Petersburg", allRegions.get(0).getName());
        assertEquals("SBp", allRegions.get(0).getShortName());
        assertEquals(region1.getId(), allRegions.get(0).getId());
    }
}