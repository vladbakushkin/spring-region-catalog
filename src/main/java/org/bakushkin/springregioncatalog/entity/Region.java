package org.bakushkin.springregioncatalog.entity;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Region {
    private Long id;
    private String name;
    private String shortName;
}
