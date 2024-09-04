package org.bakushkin.springregioncatalog.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bakushkin.springregioncatalog.controller.dto.NewRegionDto;
import org.bakushkin.springregioncatalog.controller.dto.RegionDto;
import org.bakushkin.springregioncatalog.service.RegionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/regions")
@RequiredArgsConstructor
@Slf4j
public class RegionController {

    private final RegionService regionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RegionDto addRegion(@RequestBody @Valid NewRegionDto newRegionDto) {
        log.info("adding new region: newRegionDto={}", newRegionDto);
        return regionService.addRegion(newRegionDto);
    }

    @GetMapping("{regionId}")
    public RegionDto getRegion(@PathVariable Long regionId) {
        log.info("getting region: regionId={}", regionId);
        return regionService.getRegion(regionId);
    }

    @PatchMapping("{regionId}")
    public RegionDto updateRegion(@PathVariable Long regionId,
                                  @RequestBody @Valid RegionDto regionDto) {
        log.info("updating region: regionId={}", regionId);
        return regionService.updateRegion(regionId, regionDto);
    }

    @DeleteMapping("{regionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRegion(@PathVariable Long regionId) {
        log.info("deleting region: regionId={}", regionId);
        regionService.deleteRegion(regionId);
    }

    @GetMapping
    public List<RegionDto> getAllRegions() {
        log.info("getting all regions");
        return regionService.getAllRegions();
    }
}
