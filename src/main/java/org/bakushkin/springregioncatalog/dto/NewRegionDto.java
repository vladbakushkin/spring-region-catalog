package org.bakushkin.springregioncatalog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные для создания нового региона")
public class NewRegionDto {

    @NotBlank
    @Size(max = 255)
    @Schema(description = "Полное наименование региона", example = "Saint-Petersburg", maxLength = 255)
    private String name;

    @NotBlank
    @Size(max = 63)
    @Schema(description = "Краткое наименование региона", example = "SPb", maxLength = 63)
    private String shortName;
}
