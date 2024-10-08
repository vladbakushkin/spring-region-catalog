package org.bakushkin.springregioncatalog.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bakushkin.springregioncatalog.dto.NewRegionDto;
import org.bakushkin.springregioncatalog.dto.RegionDto;
import org.bakushkin.springregioncatalog.dto.UpdateRegionDto;
import org.bakushkin.springregioncatalog.exception.ErrorResponse;
import org.bakushkin.springregioncatalog.service.RegionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "region_api")
@RestController
@RequestMapping("/regions")
@RequiredArgsConstructor
@Slf4j
public class RegionController {

    private final RegionService regionService;

    @Operation(
            summary = "Добавляет новый регион в справочник",
            description = "Позволяет добавить новый регион с указанием полного и краткого наименования."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Регион успешно добавлен",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RegionDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректные данные в запросе",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Bad Request",
                                    value = "{ \"message\": \"Invalid input data\"," +
                                            " \"reason\": \"Field 'name' cannot be blank\"," +
                                            " \"status\": \"400 BAD_REQUEST\"," +
                                            " \"timestamp\": \"2024-09-05T23:35:20.236279100\" }"
                            )
                    )
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RegionDto addRegion(@RequestBody @Valid NewRegionDto newRegionDto) {
        log.info("adding new region: newRegionDto={}", newRegionDto);
        return regionService.addRegion(newRegionDto);
    }

    @Operation(
            summary = "Получает регион по указанному id из справочника",
            description = "Возвращает подробную информацию о регионе, указанном по его идентификатору."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Регион успешно найден",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RegionDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Регион с данным ID не найден",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Not Found",
                                    value = "{ \"message\": \"Region with id 1 not found\"," +
                                            " \"reason\": \"The required object was not found.\"," +
                                            " \"status\": \"404 NOT_FOUND\"," +
                                            " \"timestamp\": \"2024-09-05T23:35:20.236279100\" }"
                            )
                    )
            )
    })
    @GetMapping("{regionId}")
    public RegionDto getRegion(@PathVariable Long regionId) {
        log.info("getting region: regionId={}", regionId);
        return regionService.getRegion(regionId);
    }

    @Operation(
            summary = "Обновляет свойства региона по указанному id в справочнике",
            description = "Позволяет частично обновить свойства региона по его идентификатору. Если какое-либо из свойств не указано, оно не будет изменено."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Регион успешно обновлён",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RegionDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректные данные в запросе",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Bad Request",
                                    value = "{ \"message\": \"Invalid input data\"," +
                                            " \"reason\": \"Field 'name' cannot be blank\"," +
                                            " \"status\": \"400 BAD_REQUEST\"," +
                                            " \"timestamp\": \"2024-09-05T23:35:20.236279100\" }"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Регион с данным ID не найден",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Not Found",
                                    value = "{ \"message\": \"Region with id 1 not found\"," +
                                            " \"reason\": \"The required object was not found.\"," +
                                            " \"status\": \"404 NOT_FOUND\"," +
                                            " \"timestamp\": \"2024-09-05T23:35:20.236279100\" }"
                            )
                    )
            )
    })
    @PatchMapping("{regionId}")
    public RegionDto updateRegion(@PathVariable Long regionId,
                                  @RequestBody @Valid UpdateRegionDto regionDto) {
        log.info("updating region: regionId={}", regionId);
        return regionService.updateRegion(regionId, regionDto);
    }

    @Operation(
            summary = "Удаляет регион по указанному id из справочника",
            description = "Позволяет удалить регион по указанному идентификатору. " +
                    "Возвращает код 204 при успешном удалении."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Регион успешно удалён",
                    content = @Content(mediaType = "application/json")
            )
    })
    @DeleteMapping("{regionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRegion(@PathVariable Long regionId) {
        log.info("deleting region: regionId={}", regionId);
        regionService.deleteRegion(regionId);
    }

    @Operation(
            summary = "Получает все регионы из справочника",
            description = "Возвращает список регионов, содержащихся в базе данных. " +
                    "Параметры пагинации limit и offset используются для определения количества " +
                    "возвращаемых записей и смещения. Если регионы не найдены - возвращает пустой список."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список регионов успешно получен",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RegionDto.class)),
                            examples = @ExampleObject(
                                    name = "Regions List",
                                    value = "[{ \"id\": 1," +
                                            " \"name\": \"Saint-Petersburg\"," +
                                            " \"shortName\": \"SPb\" }, " +
                                            "{ \"id\": 2, " +
                                            "\"name\": \"Moscow\"," +
                                            " \"shortName\": \"MSK\" }]"
                            )
                    )
            )
    })
    @GetMapping
    public List<RegionDto> getAllRegions(@Positive @RequestParam(defaultValue = "10") Integer limit,
                                         @PositiveOrZero @RequestParam(defaultValue = "0") Integer offset) {
        log.info("getting all regions with limit {} and offset {}", limit, offset);
        return regionService.getAllRegions(limit, offset);
    }
}
