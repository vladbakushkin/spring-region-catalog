package org.bakushkin.springregioncatalog.util;

import org.bakushkin.springregioncatalog.controller.dto.NewRegionDto;
import org.bakushkin.springregioncatalog.controller.dto.RegionDto;
import org.bakushkin.springregioncatalog.entity.Region;

public final class RegionDtoMapper {

    private RegionDtoMapper() {

    }

    public static RegionDto toRegionDto(final Region region) {
        if (region == null) {
            return null;
        }

        RegionDto regionDto = new RegionDto();
        regionDto.setId(region.getId());
        regionDto.setName(region.getName());
        regionDto.setShortName(region.getShortName());

        return regionDto;
    }

    public static Region toRegion(final NewRegionDto newRegionDto) {
        if (newRegionDto == null) {
            return null;
        }

        Region region = new Region();
        region.setName(newRegionDto.getName());
        region.setShortName(newRegionDto.getShortName());

        return region;
    }
}
