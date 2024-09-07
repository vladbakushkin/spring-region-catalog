package org.bakushkin.springregioncatalog.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные региона")
public class RegionDto {

    @Schema(description = "Идентификатор региона", example = "1")
    private Long id;

    @Size(max = 255)
    @Schema(description = "Название региона", example = "Saint-Petersburg")
    private String name;

    @Size(max = 63)
    @Schema(description = "Краткое название региона", example = "SPb")
    private String shortName;
}
