package org.bakushkin.springregioncatalog.controller.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegionDto {

    private Long id;

    @Size(max = 255)
    private String name;

    @Size(max = 63)
    private String shortName;
}
