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
}