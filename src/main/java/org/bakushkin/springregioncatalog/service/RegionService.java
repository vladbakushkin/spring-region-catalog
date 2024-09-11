package org.bakushkin.springregioncatalog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bakushkin.springregioncatalog.dto.NewRegionDto;
import org.bakushkin.springregioncatalog.dto.RegionDto;
import org.bakushkin.springregioncatalog.dto.UpdateRegionDto;
import org.bakushkin.springregioncatalog.entity.Region;
import org.bakushkin.springregioncatalog.exception.NotFoundException;
import org.bakushkin.springregioncatalog.mapper.RegionMapper;
import org.bakushkin.springregioncatalog.util.RegionDtoMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegionService {

    private final RegionMapper regionMapper;

    @Transactional
    @Cacheable(value = "regions", key = "#newRegionDto.name")
    public RegionDto addRegion(NewRegionDto newRegionDto) {
        Region region = RegionDtoMapper.toRegion(newRegionDto);
        regionMapper.save(region);
        log.info("saved region: {}", region);
        return RegionDtoMapper.toRegionDto(region);
    }

    @Transactional(readOnly = true)
    @Cacheable("regions")
    public RegionDto getRegion(Long regionId) {
        Region region = findRegionById(regionId);
        log.info("found region: {}", region);
        return RegionDtoMapper.toRegionDto(region);
    }

    @Transactional
    @CacheEvict(value = "regions", key = "#regionId")
    public RegionDto updateRegion(Long regionId, UpdateRegionDto regionDto) {
        Region regionToUpdate = findRegionById(regionId);

        if (regionDto.getName() != null) {
            regionToUpdate.setName(regionDto.getName());
        }

        if (regionDto.getShortName() != null) {
            regionToUpdate.setShortName(regionDto.getShortName());
        }

        regionMapper.update(regionToUpdate);
        log.info("updated region: {}", regionToUpdate);
        return RegionDtoMapper.toRegionDto(regionToUpdate);
    }

    @Transactional
    @CacheEvict(value = "regions", key = "#regionId")
    public void deleteRegion(Long regionId) {
        regionMapper.delete(regionId);
        log.info("deleted region: {}", regionId);
    }

    @Transactional(readOnly = true)
    public List<RegionDto> getAllRegions(Integer limit, Integer offset) {
        List<Region> regions = regionMapper.findAll(limit, offset);

        log.info("found regions: {}", regions);

        return regions.stream()
                .map(RegionDtoMapper::toRegionDto)
                .collect(Collectors.toList());
    }

    private Region findRegionById(Long regionId) {
        Region region = regionMapper.findById(regionId);
        if (region == null) {
            throw new NotFoundException("Region with id " + regionId + " not found");
        }
        return region;
    }
}
