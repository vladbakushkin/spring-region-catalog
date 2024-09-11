package org.bakushkin.springregioncatalog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bakushkin.springregioncatalog.dto.NewRegionDto;
import org.bakushkin.springregioncatalog.dto.RegionDto;
import org.bakushkin.springregioncatalog.entity.Region;
import org.bakushkin.springregioncatalog.mapper.RegionMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.DirtiesContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Slf4j
class RegionServiceCacheTest {

    private final RegionService regionService;

    @SpyBean
    private RegionMapper regionMapper;

    @Test
    public void get() {
        // given
        regionService.addRegion(new NewRegionDto("Saint-Petersburg", "SPb"));
        regionService.addRegion(new NewRegionDto("Moscow", "MSK"));

        // when
        getAndPrint(1L);
        getAndPrint(2L);
        getAndPrint(1L);
        getAndPrint(2L);

        // then
        verify(regionMapper, times(1)).findById(1L);
        verify(regionMapper, times(1)).findById(2L);
    }

    private void getAndPrint(Long id) {
        log.info("getting region by id {}, found: {}", id, regionService.getRegion(id));
    }

    @Test
    public void checkSettings() throws InterruptedException {
        RegionDto regionSpb1 = regionService.addRegion(new NewRegionDto("Saint-Petersburg", "SPb"));
        log.info("found region from test: {}", regionService.getRegion(regionSpb1.getId()));

        RegionDto regionSpb2 = regionService.addRegion(new NewRegionDto("Saint-Petersburg", "SPb"));
        log.info("found region from test: {}", regionService.getRegion(regionSpb2.getId()));

        Thread.sleep(2000L);

        RegionDto regionSpb3 = regionService.addRegion(new NewRegionDto("Saint-Petersburg", "SPb"));
        log.info("found region from test: {}", regionService.getRegion(regionSpb3.getId()));

        verify(regionMapper, times(2)).save(any(Region.class));
        verify(regionMapper, times(2)).findById(any(Long.class));
    }
}